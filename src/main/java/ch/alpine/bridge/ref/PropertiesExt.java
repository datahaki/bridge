// code by jph
package ch.alpine.bridge.ref;

import java.util.HexFormat;
import java.util.Properties;

/* package */ enum PropertiesExt {
  ;
  /** code extracted as-is from {@link Properties}
   * 
   * @param theString
   * @param escapeSpace
   * @param escapeUnicode
   * @return */
  public static String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
    int len = theString.length();
    int bufLen = len * 2;
    if (bufLen < 0) {
      bufLen = Integer.MAX_VALUE;
    }
    StringBuilder outBuffer = new StringBuilder(bufLen);
    HexFormat hex = HexFormat.of().withUpperCase();
    for (int x = 0; x < len; x++) {
      char aChar = theString.charAt(x);
      // Handle common case first, selecting largest block that
      // avoids the specials below
      if ((aChar > 61) && (aChar < 127)) {
        if (aChar == '\\') {
          outBuffer.append('\\');
          outBuffer.append('\\');
          continue;
        }
        outBuffer.append(aChar);
        continue;
      }
      switch (aChar) {
      case ' ':
        if (x == 0 || escapeSpace)
          outBuffer.append('\\');
        outBuffer.append(' ');
        break;
      case '\t':
        outBuffer.append('\\');
        outBuffer.append('t');
        break;
      case '\n':
        outBuffer.append('\\');
        outBuffer.append('n');
        break;
      case '\r':
        outBuffer.append('\\');
        outBuffer.append('r');
        break;
      case '\f':
        outBuffer.append('\\');
        outBuffer.append('f');
        break;
      case '=': // Fall through
      case ':': // Fall through
      case '#': // Fall through
      case '!':
        outBuffer.append('\\');
        outBuffer.append(aChar);
        break;
      default:
        if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
          outBuffer.append("\\u");
          outBuffer.append(hex.toHexDigits(aChar));
        } else {
          outBuffer.append(aChar);
        }
      }
    }
    return outBuffer.toString();
  }
}
