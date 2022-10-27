// code by legion
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.tensor.sca.Clip;

abstract class Axis {
  static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  static final Color COLOR_GRIDLINES = new Color(224, 224, 224);
  /** ..... */
  static final Color COLOR_HELPER = new Color(192, 192, 192);
  // ---
  final DateTimeFocus dateTimeFocus;
  boolean gridLines = true;
  boolean ticks = true;

  public Axis(DateTimeFocus dateTimeFocus) {
    this.dateTimeFocus = dateTimeFocus;
  }

  final void render(ShowableConfig showableConfig, Point point, int length, Graphics _g, Clip clip) {
    Graphics2D graphics = (Graphics2D) _g.create();
    protected_render(showableConfig, point, length, graphics, clip);
    graphics.dispose();
  }

  abstract void protected_render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics, Clip clip);
}
