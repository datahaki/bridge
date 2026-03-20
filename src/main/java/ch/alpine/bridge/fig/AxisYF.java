// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map.Entry;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;

// TODO BRIDGE only used for BarLegend, ticks are draw to right instead of left
class AxisYF extends AxisY {
  public AxisYF(ConfBase confBase, AxisOptions axisOptions) {
    super(confBase, axisOptions);
  }

  @Override
  protected void drawAxisTick(Graphics2D graphics, Point point, int pixel) {
    graphics.drawLine(point.x + 1, pixel, point.x + 2, pixel);
  }

  @Override
  protected void protected_render(Graphics2D graphics, Point point) {
    // formula showableConfig.y_pos does not apply here due to different clip
    // so we have to compute y_pos explicitly
    FontMetrics fontMetrics = graphics.getFontMetrics();
    graphics.setColor(StaticHelper.COLOR_FONT);
    for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
      int piy = entry.getKey();
      Scalar value = entry.getValue();
      String yLabel = Objects.isNull(dateTimeFormatter) //
          ? Ticks.format(value)
          : ((DateTime) value).format(dateTimeFormatter);
      graphics.drawString(yLabel, //
          point.x + 5, //
          piy + fontMetrics.getAscent() / 2 - 1);
    }
  }
}
