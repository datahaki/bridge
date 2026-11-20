// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.Graphics2D;
import java.awt.Point;

/** y-axis with labels right of axis */
public class AxisYR extends AxisY {
  public AxisYR(AxisOptions axisOptions) {
    super(axisOptions);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(point.x + 1, pixel, point.x + 2, pixel);
  }

  @Override
  int stringx(int x, int width) {
    return x + GAP;
  }
}
