// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
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
  void protected_render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics, Clip clip) {
    Rectangle rectangle = showableConfig.rectangle;
    FontMetrics fontMetrics = graphics.getFontMetrics();
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    DateTimeFormatter dateTimeFormatter = null;
    if (clip.min() instanceof DateTime) {
      // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(RationalScalar.of(100, rectangle.width)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int x_pos = (int) showableConfig.x_pos(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      // TODO BRIDGE determine reserve, instead of 50 hardcode
      Scalar dX = StaticHelper.getDecimalStep(clip.width().divide(RealScalar.of(rectangle.width)).multiply(RealScalar.of(50)));
      for ( //
          Scalar xValue = Ceiling.toMultipleOf(dX).apply(clip.min()); //
          Scalars.lessEquals(xValue, clip.max()); //
          xValue = xValue.add(dX)) {
        int x_pos = (int) showableConfig.x_pos(xValue);
        navigableMap.put(x_pos, xValue);
      }
    }
    if (gridLines) { // grid lines |
      graphics.setColor(COLOR_GRIDLINES);
      graphics.setStroke(STROKE_GRIDLINES);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, rectangle.y + rectangle.height);
    }
    if (ticks) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine( //
            point.x, //
            point.y, //
            point.x + length - 1, //
            point.y);
        for (int pix : navigableMap.keySet())
          graphics.drawLine(pix, point.y + 1, pix, point.y + 2);
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
              point.y + 3 + fontMetrics.getAscent());
        }
      }
    }
  }
}
