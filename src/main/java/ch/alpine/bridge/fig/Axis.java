// code by legion
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

abstract class Axis {
  protected static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  protected static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  protected static final Color COLOR_HELPER = new Color(192, 192, 192);
  // TODO BRIDGE determine reserve, instead of 50 hardcode
  protected static final Scalar RESERVE = RealScalar.of(50);
  // ---
  protected final ShowOptions showOptions;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 12);

  public Axis(ShowOptions showOptions) {
    this.showOptions = showOptions;
  }

  /** @param showableConfig
   * @param point
   * @param length
   * @param _g */
  protected final void render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    RenderQuality.smoothLine(graphics, false);
    protected_render(showableConfig, point, graphics);
    RenderQuality.smoothLine(graphics, true);
  }

  protected abstract void protected_render(ShowableConfig showableConfig, Point point, Graphics2D graphics);

  public final void setFont(Font font) {
    this.font = font;
  }

  public final Font getFont() {
    return font;
  }

  protected static int interval(FontMetrics fontMetrics) {
    return fontMetrics.getAscent() * 8 / 5;
  }
}
