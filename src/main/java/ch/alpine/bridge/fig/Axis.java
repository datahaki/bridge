// code by legion
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

abstract class Axis {
  protected static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  protected static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  protected static final Color COLOR_HELPER = new Color(192, 192, 192);
  protected static final Scalar RESERVE = RealScalar.of(50);
  // ---
  protected final ShowOptions showOptions;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 12);

  public Axis(ShowOptions showOptions) {
    this.showOptions = showOptions;
  }

  protected final void render(ShowableConfig showableConfig, Point point, int length, Graphics _g, Clip clip) {
    Graphics2D graphics = (Graphics2D) _g.create();
    protected_render(showableConfig, point, length, graphics, clip);
    graphics.dispose();
  }

  public final void setFont(Font font) {
    this.font = font;
  }

  public final Font getFont() {
    return font;
  }

  protected abstract void protected_render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics, Clip clip);
}
