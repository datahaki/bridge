// code by jph
package ch.alpine.bridge.awt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public enum RenderQuality {
  ;
  /** use for publishing quality
   * suggestions by chatgpt
   * 
   * @param graphics */
  public static void setQuality(Graphics2D graphics) {
    graphics.setRenderingHint( //
        RenderingHints.KEY_ANTIALIASING, //
        RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint( //
        RenderingHints.KEY_RENDERING, //
        RenderingHints.VALUE_RENDER_QUALITY);
    graphics.setRenderingHint( //
        RenderingHints.KEY_INTERPOLATION, //
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    // at times produces "spaces"
    // graphics.setRenderingHint( //
    // RenderingHints.KEY_TEXT_ANTIALIASING, //
    // RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    graphics.setRenderingHint( //
        RenderingHints.KEY_STROKE_CONTROL, //
        RenderingHints.VALUE_STROKE_PURE);
    graphics.setRenderingHint( //
        RenderingHints.KEY_FRACTIONALMETRICS, //
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
  }

  /** use for publishing quality
   * suggestions by chatgpt
   * 
   * @param graphics */
  public static void setQuality(Graphics g) {
    if (g instanceof Graphics2D graphics)
      setQuality(graphics);
  }
}
