// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map.Entry;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;

class AxisY extends Axis {
  public AxisY(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel) {
    graphics.drawLine(rectangle.x, pixel, rectangle.x + rectangle.width - 1, pixel);
  }

  @Override
  protected void drawAxisLine(Graphics2D graphics, Point point) {
    graphics.drawLine(point.x, point.y, point.x, point.y + confBase.width - 1);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(point.x - 2, pixel, point.x - 1, pixel);
  }

  /** draw lines and numbers like this: _________________ */
  @Override
  protected void protected_render(Graphics2D graphics, Point point) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    graphics.setColor(StaticHelper.COLOR_FONT);
    for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
      int piy = entry.getKey();
      Scalar value = entry.getValue();
      String yLabel = Objects.isNull(dateTimeFormatter) //
          ? Ticks.format(value)
          : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(yLabel, //
          point.x - fontMetrics.stringWidth(yLabel) - 5, //
          piy + fontMetrics.getAscent() / 2 - 1);
    }
  }
}
