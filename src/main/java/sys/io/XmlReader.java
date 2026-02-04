// code by jph
package sys.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.alpine.bridge.lang.FriendlyFormat;

public abstract class XmlReader {
  /** guess UTF-8 ideally the charset should be taken from the first line, for instance:
   * <?xml version="1.0" encoding='UTF-8' standalone='no' ?> */
  public static final Charset CHARSET = StandardCharsets.UTF_8;
  private static final Pattern PATTERN = Pattern.compile("<.+?>");
  // ---
  private Deque<String> stack = null;
  private int index;

  /** override to access comments
   * 
   * @param group */
  protected void comment(String group) {
    // empty by default
  }

  /** @param token
   * @param string is trimmed
   * @throws Exception */
  protected abstract void string(String token, String string) throws Exception;

  protected abstract void pop(String token) throws Exception;

  /** token != peek()
   * 
   * @param token
   * @param group
   * @throws Exception */
  protected abstract void pushpop(String token, String group) throws Exception;

  protected abstract void push(String token, String group) throws Exception;

  /** @param inputStream is not closed inside parse function, but should to be closed outside
   * to allow for processing of ZipEntry
   * @throws Exception */
  public final void parse(InputStream inputStream) throws Exception {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
    stack = new ArrayDeque<>();
    index = 0;
    StringBuilder lineBuilder = new StringBuilder();
    String line;
    try {
      while (Objects.nonNull(line = bufferedReader.readLine())) {
        lineBuilder.append(line);
        Matcher matcher = PATTERN.matcher(lineBuilder);
        while (matcher.find()) {
          StringBuilder stringBuilder = new StringBuilder();
          matcher.appendReplacement(stringBuilder, "");
          {
            String content = stringBuilder.toString().trim();
            if (!content.isEmpty())
              if (stack.isEmpty()) {
                System.out.println("[" + index + "] " + lineBuilder);
                System.out.println(content);
              } else {
                String token = stack.pop();
                try {
                  string(token, FriendlyFormat.convertAmps(content));
                } catch (Exception exception) {
                  exception.printStackTrace();
                }
                stack.push(token);
              }
          }
          // ---
          String group = matcher.group();
          if (group.startsWith("</")) { // closure
            String token = group.substring(2).split(" |>")[0];
            String verif = stack.pop();
            if (!verif.equals(token))
              throw new RuntimeException(verif + " != " + token);
            pop(token);
          } else //
          if (group.startsWith("<!") || group.startsWith("<?")) { // comment
            comment(group);
          } else //
          if (group.endsWith("/>")) {
            String token = group.substring(1).split(" |/")[0];
            pushpop(token, group);
          } else { // opening
            String token = group.substring(1).split(" |>")[0];
            push(token, group);
            stack.push(token);
          }
        }
        lineBuilder = new StringBuilder();
        matcher.appendTail(lineBuilder);
        ++index;
      }
      if (!stack.isEmpty()) {
        throw new RuntimeException("xml file corrupt: " + stack);
      }
    } finally {
      stack = null;
    }
  }

  protected static String getValue(String group, String key) {
    Pattern pattern = Pattern.compile("\\s" + key + "\\s*=");
    Matcher matcher = pattern.matcher(group);
    if (matcher.find()) {
      int beg = group.indexOf('\"', matcher.end()) + 1;
      int end = group.indexOf('\"', beg);
      return group.substring(beg, end);
    }
    return null;
  }

  protected int getLine() {
    return index;
  }

  protected String peek() {
    return stack.peek();
  }

  protected boolean contains(String token) {
    return stack.contains(token);
  }
}
