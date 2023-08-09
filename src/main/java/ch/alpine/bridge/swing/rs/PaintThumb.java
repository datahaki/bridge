// code by clruch, jph
package ch.alpine.bridge.swing.rs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/* package */ enum PaintThumb {
  ;
  private static final Color GRAY = new Color(220, 220, 220);

  public static void using(Graphics _g, Rectangle rectangle) {
    paint(_g, rectangle, GRAY, Color.BLACK);
  }

  private static void paint(Graphics _g, Rectangle rectangle, Color colorFill, Color colorLine) {
    int w = rectangle.width - 1;
    int h = rectangle.height - 1;
    Graphics graphics = _g.create();
    graphics.setColor(colorFill);
    graphics.fillArc(rectangle.x, rectangle.y, w, h, 0, 360);
    graphics.setColor(colorLine);
    graphics.drawArc(rectangle.x, rectangle.y, w, h, 0, 360);
    graphics.dispose();
  }
}
