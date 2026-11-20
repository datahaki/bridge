// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Map.Entry;

import ch.alpine.bridge.fig.ShowOptions;
import ch.alpine.bridge.fig.ShowableConfig;

abstract class AxisY extends Axis {
  static final int GAP = 5;

  public AxisY(AxisOptions axisOptions) {
    super(axisOptions);
  }

  @Override
  protected final void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel) {
    graphics.drawLine(rectangle.x, pixel, rectangle.x + rectangle.width - 1, pixel);
  }

  @Override
  protected final void drawAxisLine(Graphics2D graphics, Point point, int pixel_len) {
    graphics.drawLine(point.x, point.y, point.x, point.y + pixel_len - 1);
  }

  @Override
  protected final void protected_render(Graphics2D graphics, Point point, Map<Integer, String> map) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
    graphics.setColor(ShowOptions.COLOR_FONT);
    for (Entry<Integer, String> entry : map.entrySet()) {
      String yLabel = entry.getValue();
      // String yLabel = Objects.isNull(dateTimeFormatter) //
      // ? format(value)
      // : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(yLabel, //
          stringx(point.x, fontMetrics.stringWidth(yLabel)), //
          (int) (entry.getKey() + delta_y));
    }
  }

  @Override
  protected final AxisConfig axisConfig(ShowableConfig showableConfig) {
    return showableConfig.axisConfigY();
  }

  /** @param x
   * @param width
   * @return x-coordinate of where to draw the string */
  abstract int stringx(int x, int width);
}
