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

abstract class AxisY extends Axis {
  public AxisY(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected final void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel) {
    graphics.drawLine(rectangle.x, pixel, rectangle.x + rectangle.width - 1, pixel);
  }

  @Override
  protected final void drawAxisLine(Graphics2D graphics, Point point) {
    graphics.drawLine(point.x, point.y, point.x, point.y + confBase.length() - 1);
  }

  @Override
  protected final void protected_render(Graphics2D graphics, Point point) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
    graphics.setColor(StaticHelper.COLOR_FONT);
    for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
      Scalar value = entry.getValue();
      String yLabel = Objects.isNull(dateTimeFormatter) //
          ? Ticks.format(value)
          : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(yLabel, //
          stringx(point.x, fontMetrics.stringWidth(yLabel)), //
          (int) (entry.getKey() + delta_y));
    }
  }

  abstract int stringx(int x, int width);
}
