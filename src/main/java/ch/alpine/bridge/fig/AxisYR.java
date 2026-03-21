// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Point;

class AxisYR extends AxisY {
  public AxisYR(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(point.x + 1, pixel, point.x + 2, pixel);
  }

  @Override
  int stringx(int x, int width) {
    return x + 5;
  }
}
