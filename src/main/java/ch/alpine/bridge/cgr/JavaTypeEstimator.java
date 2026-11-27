// code by gemini
package ch.alpine.bridge.cgr;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaTypeEstimator {
  public enum DeclaredType {
    INTERFACE,
    SEALED_INTERFACE,
    CLASS,
    SEALED_CLASS,
    ENUM,
    RECORD,
    SEALED_RECORD,
    ANNOTATION,
    UNKNOWN;

    public boolean isInterface() {
      return equals(INTERFACE) || equals(SEALED_INTERFACE);
    }

    public boolean isAnnotation() {
      return equals(ANNOTATION);
    }
  }

  public static DeclaredType estimateType(Path filePath) {
    try {
      String code = Files.readString(filePath);
      return estimateType(code);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static DeclaredType estimateType(String code) {
    StringBuilder word = new StringBuilder();
    int n = code.length();
    int i = 0;
    int braceDepth = 0;
    boolean sawAt = false;
    boolean sawSealed = false;
    DeclaredType nestedFallback = null;
    while (i < n) {
      char c = code.charAt(i);
      // Line comment
      if (c == '/' && i + 1 < n && code.charAt(i + 1) == '/') {
        var result = consumeToken(word, sawAt, sawSealed, code, i + 2, braceDepth, nestedFallback);
        if (result.topLevelMatch() != null) {
          return result.topLevelMatch();
        }
        nestedFallback = result.nestedFallback();
        sawAt = result.nextSawAt();
        sawSealed = result.nextSawSealed();
        i += 2;
        while (i < n && code.charAt(i) != '\n') {
          i++;
        }
        continue;
      }
      // Block comment
      if (c == '/' && i + 1 < n && code.charAt(i + 1) == '*') {
        var result = consumeToken(word, sawAt, sawSealed, code, i + 2, braceDepth, nestedFallback);
        if (result.topLevelMatch() != null) {
          return result.topLevelMatch();
        }
        nestedFallback = result.nestedFallback();
        sawAt = result.nextSawAt();
        sawSealed = result.nextSawSealed();
        i += 2;
        while (i + 1 < n && !(code.charAt(i) == '*' && code.charAt(i + 1) == '/')) {
          i++;
        }
        i += 2;
        continue;
      }
      // Text block (""")
      if (c == '"' && i + 2 < n && code.charAt(i + 1) == '"' && code.charAt(i + 2) == '"') {
        word.setLength(0);
        sawAt = false;
        sawSealed = false;
        i += 3;
        while (i + 2 < n && !(code.charAt(i) == '"' && code.charAt(i + 1) == '"' && code.charAt(i + 2) == '"')) {
          if (code.charAt(i) == '\\') {
            i++;
          }
          i++;
        }
        i += 3;
        continue;
      }
      // String or char literal
      if (c == '"' || c == '\'') {
        word.setLength(0);
        sawAt = false;
        sawSealed = false;
        char quote = c;
        i++;
        while (i < n && code.charAt(i) != quote) {
          if (code.charAt(i) == '\\') {
            i++;
          }
          i++;
        }
        i++;
        continue;
      }
      if (Character.isJavaIdentifierPart(c)) {
        word.append(c);
      } else {
        var result = consumeToken(word, sawAt, sawSealed, code, i + 1, braceDepth, nestedFallback);
        if (result.topLevelMatch() != null) {
          return result.topLevelMatch();
        }
        nestedFallback = result.nestedFallback();
        sawAt = result.nextSawAt();
        sawSealed = result.nextSawSealed();
        if (c == '@') {
          sawAt = true;
          sawSealed = false;
        } else if (c == '{') {
          braceDepth++;
          sawAt = false;
          sawSealed = false;
        } else if (c == '}') {
          braceDepth = Math.max(0, braceDepth - 1);
          sawAt = false;
          sawSealed = false;
        } else if (!Character.isWhitespace(c)) {
          sawAt = false;
          sawSealed = false;
        }
      }
      i++;
    }
    var tailResult = consumeToken(word, sawAt, sawSealed, code, n, braceDepth, nestedFallback);
    if (tailResult.topLevelMatch() != null) {
      return tailResult.topLevelMatch();
    }
    return tailResult.nestedFallback() != null ? tailResult.nestedFallback() : DeclaredType.UNKNOWN;
  }

  private record ScanState(DeclaredType topLevelMatch, DeclaredType nestedFallback, boolean nextSawAt, boolean nextSawSealed) {
  }

  private static ScanState consumeToken(StringBuilder word, boolean sawAt, boolean sawSealed, String code, int afterIndex, int braceDepth,
      DeclaredType nestedFallback) {
    if (word.isEmpty()) {
      return new ScanState(null, nestedFallback, sawAt, sawSealed);
    }
    String token = word.toString();
    word.setLength(0);
    if ("sealed".equals(token) && startsWithIdentifier(code, afterIndex)) {
      return new ScanState(null, nestedFallback, false, true);
    }
    DeclaredType matched = switch (token) {
    case "interface" -> sawAt ? DeclaredType.ANNOTATION : (sawSealed ? DeclaredType.SEALED_INTERFACE : DeclaredType.INTERFACE);
    case "class" -> sawSealed ? DeclaredType.SEALED_CLASS : DeclaredType.CLASS;
    case "enum" -> DeclaredType.ENUM;
    case "record" -> startsWithIdentifier(code, afterIndex) ? (sawSealed ? DeclaredType.SEALED_RECORD : DeclaredType.RECORD) : null;
    default -> null;
    };
    if (matched != null) {
      if (braceDepth == 0) {
        return new ScanState(matched, nestedFallback, false, false);
      }
      DeclaredType updatedFallback = nestedFallback != null ? nestedFallback : matched;
      return new ScanState(null, updatedFallback, false, false);
    }
    return new ScanState(null, nestedFallback, false, false);
  }

  private static boolean startsWithIdentifier(String code, int idx) {
    int n = code.length();
    while (idx < n) {
      char c = code.charAt(idx);
      if (Character.isWhitespace(c)) {
        idx++;
      } else if (c == '/' && idx + 1 < n && code.charAt(idx + 1) == '/') {
        idx += 2;
        while (idx < n && code.charAt(idx) != '\n') {
          idx++;
        }
      } else if (c == '/' && idx + 1 < n && code.charAt(idx + 1) == '*') {
        idx += 2;
        while (idx + 1 < n && !(code.charAt(idx) == '*' && code.charAt(idx + 1) == '/')) {
          idx++;
        }
        idx += 2;
      } else {
        return Character.isJavaIdentifierStart(c);
      }
    }
    return false;
  }
}
