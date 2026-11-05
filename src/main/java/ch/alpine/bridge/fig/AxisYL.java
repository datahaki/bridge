// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Point;

class AxisYL extends AxisY {
  public AxisYL(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(point.x - 2, pixel, point.x - 1, pixel);
  }

  @Override
  int stringx(int x, int width) {
    return x - width - GAP;
  }
}
