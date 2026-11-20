// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import ch.alpine.bridge.fig.ShowOptions;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;

class AxisX extends Axis {
  public AxisX(AxisOptions axisOptions) {
    super(axisOptions);
  }

  @Override
  protected void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel) {
    graphics.drawLine(pixel, rectangle.y, pixel, rectangle.y + rectangle.height - 1);
  }

  @Override
  protected void drawAxisLine(Graphics2D graphics, Point point, int pixel_len) {
    graphics.drawLine(point.x, point.y, point.x + pixel_len - 1, point.y);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(pixel, point.y + 1, pixel, point.y + 2);
  }

  @Override
  protected void protected_render(Graphics2D graphics, Point point, Map<Integer, Scalar> navigableMap) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    graphics.setColor(ShowOptions.COLOR_FONT);
    for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
      Scalar value = entry.getValue();
      String xLabel = Objects.isNull(dateTimeFormatter) //
          ? format(value)
          : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(xLabel, //
          entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
          point.y + ShowOptions.TICK + fontMetrics.getAscent());
    }
  }

  @Override
  protected final AxisConfig axisConfig(ShowableConfig showableConfig) {
    return showableConfig.confX();
  }
}
