// code by jph
package sys.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/** "GlyphLayout" already exists in java namespace */
public enum GlyphLayout2 {
  ;
  public static Rectangle2D getBounds(Font font, String string) {
    // TODO MIDI implementation is horrific
    BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    FontRenderContext fontRenderContext = graphics.getFontRenderContext();
    TextLayout textLayout = new TextLayout(string, font, fontRenderContext);
    graphics.dispose();
    return textLayout.getBounds();
  }
}
