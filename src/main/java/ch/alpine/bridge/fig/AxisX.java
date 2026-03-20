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

// TODO BRIDGE logarithmic scale
class AxisX extends Axis {
  public AxisX(ShowOptions showOptions) {
    super(showOptions);
  }

  @Override
  protected void protected_render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    ConfBase confBase = showableConfig.confX;
    // IO.println("AXIS X " + point + " " + showableConfig.confX);
    Rectangle rectangle = showableConfig.rectangle();
    graphics.setFont(getFont());
    TicksConfig ticksConfig = new TicksConfig(confBase, showOptions.dateTimeFocus);
    if (showOptions.contains(ShowOption.GRID)) { // grid lines |
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int pix : ticksConfig.navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, rectangle.y + rectangle.height);
    }
    {
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      graphics.drawLine( //
          point.x, //
          point.y, //
          point.x + showableConfig.confX.width - 1, //
          point.y);
      for (int pix : ticksConfig.navigableMap.keySet())
        graphics.drawLine(pix, point.y + 1, pix, point.y + 2);
    }
    {
      FontMetrics fontMetrics = graphics.getFontMetrics();
      graphics.setColor(StaticHelper.COLOR_FONT);
      for (Entry<Integer, Scalar> entry : ticksConfig.navigableMap.entrySet()) {
        Scalar value = entry.getValue();
        String xLabel = Objects.isNull(ticksConfig.dateTimeFormatter) //
            ? Ticks.format(value)
            : ((DateTime) value).format(ticksConfig.dateTimeFormatter);
        graphics.drawString(xLabel, //
            entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
            point.y + 3 + fontMetrics.getAscent());
      }
    }
  }
}
