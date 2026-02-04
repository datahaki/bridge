// code by jph
package sys.svg;

/** @param string typically consists of 1 char, i.e. string.length() == 1
 * @param d drawing instructions; for quick glyph comparison */
public record SvgGlyph(String string, String d) {
  /** @return in lower-case */
  public String toUnicodeHex() {
    StringBuilder stringBuilder = new StringBuilder();
    for (char chr : string.toCharArray())
      stringBuilder.append(String.format("%04x", (int) chr));
    return stringBuilder.toString();
  }

  @Override
  public String toString() {
    return string;
  }
}
