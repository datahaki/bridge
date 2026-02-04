// code by jph
package sys.svg;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import sys.io.XmlReader;

/** known limitations: implementation of {@link SvgFontIndex} only supports 16-bit unicode chars
 * 
 * convention in Bravura1:
 * glyph-name="uniE52E" unicode="&#xe520;&#xe522;"
 * glyph-name="uniE938_uniE943" unicode="&#xe938;&#xe943;"
 * 
 * convention in Bravura2:
 * unicode="&#xe520;&#xe522;"
 * unicode="&#xe938;&#xe943;"
 * 
 * convention in Lilypond:
 * glyph-name="accidentals.sharp" unicode="&#xe10f;" */
public class SvgFontIndex extends XmlReader {
  private String id = null;

  /** @param file svg
   * @throws Exception */
  public SvgFontIndex(File file) throws Exception {
    try (InputStream inputStream = new FileInputStream(file)) {
      read(inputStream);
    }
  }

  public int unsupported = 0;
  /** maps from glyph-name to unicode chars */
  private final Map<String, SvgGlyph> glyphNameMap = new LinkedHashMap<>();
  private final Map<String, SvgGlyph> unicodeMap = new LinkedHashMap<>();

  public void read(InputStream inputStream) throws Exception {
    parse(inputStream);
    // ---
    // if (0 < unsupported)
    // System.out.println("unsupported chars " + unsupported);
  }

  @Override
  protected void string(String token, String string) throws Exception {
    // n.a.
  }

  @Override
  protected void pop(String token) throws Exception {
    // n.a.
  }

  private static String convertToChars(String unicode) {
    StringBuilder stringBuilder = new StringBuilder();
    while (!unicode.isEmpty()) {
      if (unicode.startsWith("&#x")) {
        int index = unicode.indexOf(';');
        // ---
        if (index == 7) {
          char chr = (char) Integer.parseInt(unicode.substring(3, index), 16); // last symbol is ';'
          stringBuilder.append(chr);
          unicode = unicode.substring(index + 1);
        } else {
          // ++unsupported;
          // throw new RuntimeException();
          break;
        }
      } else { // single char
        char chr = unicode.charAt(0);
        stringBuilder.append(chr);
        unicode = unicode.substring(1);
      }
    }
    return stringBuilder.toString();
  }

  @Override
  protected void pushpop(String token, String group) throws Exception {
    if (token.equals("glyph")) {
      /** <glyph glyph-name=".notdef" d="M0 0v400h200v-400h-200zM10 10h180v380h-180v-380z" /> */
      /** <glyph glyph-name="uniE527" unicode="&#xe520;&#xe520;&#xe520;&#xe520;&#xe520;&#xe520;" ...> */
      /** <glyph glyph-name="scripts.dpedaltoe" unicode="&#xe151;" ... > */
      // unicode should always be defined for normal glyphs
      final String unicode = getValue(group, "unicode");
      if (Objects.isNull(unicode) || unicode.isEmpty()) { // || d==null || d.equals(missing_glyph_d)
        // .notdef
      } else {
        final String chrs = convertToChars(unicode);
        // d represents shape of glyph
        final String d = getValue(group, "d");
        final SvgGlyph svgGlyph = new SvgGlyph(chrs, d);
        // ---
        unicodeMap.put(chrs, svgGlyph);
        // ---
        // glyph-name is optional (for instance not present in latest Bravura)
        final String glyphName = getValue(group, "glyph-name");
        if (Objects.nonNull(glyphName))
          glyphNameMap.put(glyphName, svgGlyph);
      }
    } else //
    if (token.equals("missing-glyph")) {
      /** <missing-glyph horiz-adv-x="500" d="M50 0v533h400v-533h-400zM100 50h300v433h-300v-433z" /> */
      // missing_glyph_d = getValue(myGroup, "d");
    }
  }

  @Override
  protected void push(String token, String group) throws Exception {
    if (token.equals("font"))
      id = getValue(group, "id");
  }

  public String getId() {
    return id;
  }

  // helper functions for glyph-name index
  public boolean hasGlyphName(String string) {
    return glyphNameMap.containsKey(string);
  }

  public SvgGlyph getGlyphName(String string) {
    return glyphNameMap.get(string);
  }

  public Set<String> getGlyphNameSet() {
    return glyphNameMap.keySet();
  }

  // helper functions for unicode index
  public boolean hasUnicode(String string) {
    return unicodeMap.containsKey(string);
  }

  public SvgGlyph getUnicode(String string) {
    return unicodeMap.get(string);
  }

  /** function for consistency in case otf != svg
   * 
   * otherwise Font::canDisplay might return true, even thought char does not appear in svg list
   * 
   * @param chr
   * @return */
  public boolean isMeaningful(char chr) {
    return hasUnicode("" + chr);
  }
}
