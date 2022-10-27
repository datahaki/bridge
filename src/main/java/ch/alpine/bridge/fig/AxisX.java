// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;

class AxisX extends Axis {
  public AxisX(DateTimeFocus dateTimeFocus) {
    super(dateTimeFocus);
  }

  @Override
  void protected_render(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip xRange = showableConfig.getClip(0);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    DateTimeFormatter dateTimeFormatter = null;
    if (xRange.min() instanceof DateTime) {
      // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(xRange.width().multiply(RationalScalar.of(100, rectangle.width)));
      DateTime startAttempt = dateTimeInterval.floor(xRange.min());
      DateTime dateTime = xRange.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (xRange.isInside(dateTime)) {
        int x_pos = (int) showableConfig.x_pos(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      // TODO BRIDGE determine reserve, instead of 50 hardcode
      Scalar dX = StaticHelper.getDecimalStep(xRange.width().divide(RealScalar.of(rectangle.width)).multiply(RealScalar.of(50)));
      for (Scalar xValue = Ceiling.toMultipleOf(dX).apply(xRange.min()); Scalars.lessEquals(xValue, xRange.max()); xValue = xValue.add(dX)) {
        int x_pos = (int) showableConfig.x_pos(xValue);
        navigableMap.put(x_pos, xValue);
      }
    }
    final int y_height = rectangle.y + rectangle.height - 1;
    if (gridLines) { // grid lines |
      graphics.setColor(COLOR_GRIDLINES);
      graphics.setStroke(STROKE_GRIDLINES);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, y_height);
    }
    if (ticks) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine( //
            rectangle.x, //
            y_height + StaticHelper.GAP, //
            rectangle.x + rectangle.width - 1, //
            y_height + StaticHelper.GAP);
        for (int pix : navigableMap.keySet())
          graphics.drawLine(pix, y_height + StaticHelper.GAP + 1, pix, y_height + StaticHelper.GAP + 2);
      }
      {
        graphics.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          Scalar value = entry.getValue();
          String xLabel = Objects.isNull(dateTimeFormatter) //
              ? StaticHelper.format(value)
              : ((DateTime) value).format(dateTimeFormatter);
          graphics.drawString(xLabel, //
              entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
              y_height + StaticHelper.GAP + 3 + fontMetrics.getAscent());
        }
      }
    }
  }
}
