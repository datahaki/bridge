// code by jph
package sys.svg;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import sys.Filename;

/** next to the true-type font file, a svg file is required
 *
 * the following characters cause FontMetrics.charWidth(myChar) == 0
 * 
 * <pre>
 * Unicode Character 'ZERO WIDTH NON-JOINER' (U+200C)
 * Unicode Character 'ZERO WIDTH JOINER' (U+200D)
 * </pre> */
public class SvgFont {
  private final SvgFontIndex svgFontIndex;
  private final Font font;

  public SvgFont(File file) throws Exception {
    Filename filename = new Filename(file);
    svgFontIndex = new SvgFontIndex(filename.withExtension("svg"));
    font = Font.createFont(Font.TRUETYPE_FONT, filename.withExtension("otf"));
  }

  public SvgFontIndex svgFontIndex() {
    return svgFontIndex;
  }

  /** @param size
   * @return deriveFont */
  public final Font withSize(float size) {
    Map<TextAttribute, Object> map = new HashMap<>();
    map.put(TextAttribute.SIZE, size);
    return font.deriveFont(map);
  }

  public static boolean qualifies(File file) {
    // Filename filename = new Filename(file);
    // return file.isFile() //
    // && filename.hasExtension("svg");
    return false;
  }
}
