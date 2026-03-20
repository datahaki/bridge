// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map.Entry;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.DateTime;

// TODO BRIDGE only used for BarLegend, ticks are draw to right instead of left
class AxisYF extends Axis {
  public AxisYF(ConfBase confBase, ShowOptions showOptions) {
    super(confBase, showOptions);
  }

  @Override
  protected void protected_render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    if (Scalars.isZero(confBase.clip.width()))
      return;
    graphics.setFont(getFont());
    // formula showableConfig.y_pos does not apply here due to different clip
    // so we have to compute y_pos explicitly
    if (showOptions.contains(ShowOption.AXIS_Y)) {
      {
        int length = showableConfig.confY.width;
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine(point.x, point.y, point.x, point.y + length - 1);
        for (int piy : navigableMap.keySet())
          graphics.drawLine(point.x + 1, piy, point.x + 2, piy);
      }
      {
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
  }
}
