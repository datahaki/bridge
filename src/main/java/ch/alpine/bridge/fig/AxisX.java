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

class AxisX extends Axis {
  public AxisX(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel) {
    graphics.drawLine(pixel, rectangle.y, pixel, rectangle.y + rectangle.height - 1);
  }

  @Override
  protected void drawAxisLine(Graphics2D graphics, Point point) {
    graphics.drawLine(point.x, point.y, point.x + confBase.width - 1, point.y);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(pixel, point.y + 1, pixel, point.y + 2);
  }

  @Override
  protected void protected_render(Graphics2D graphics, Point point) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    graphics.setColor(StaticHelper.COLOR_FONT);
    for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
      Scalar value = entry.getValue();
      String xLabel = Objects.isNull(dateTimeFormatter) //
          ? Ticks.format(value)
          : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(xLabel, //
          entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
          point.y + StaticHelper.TICK + fontMetrics.getAscent());
    }
  }
}
