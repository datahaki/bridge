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
  public AxisY(ConfBase confBase, ShowOptions showOptions) {
    super(confBase, showOptions);
  }

  /** draw lines and numbers like this: _________________ */
  @Override
  protected void protected_render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    ConfBase confBase = showableConfig.confY;
    Rectangle rectangle = showableConfig.rectangle();
    graphics.setFont(getFont());
    if (showOptions.contains(ShowOption.GRID)) {
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(rectangle.x, piy, rectangle.x + rectangle.width - 1, piy);
    }
    {
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      graphics.drawLine( //
          point.x, //
          point.y, //
          point.x, //
          point.y + confBase.width - 1);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(point.x - 2, piy, point.x - 1, piy);
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
            point.x - fontMetrics.stringWidth(yLabel) - 5, //
            piy + fontMetrics.getAscent() / 2 - 1);
      }
    }
  }
}
