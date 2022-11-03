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

class AxisY extends Axis {
  public AxisY(DateTimeFocus dateTimeFocus) {
    super(dateTimeFocus);
  }

  /** draw lines and numbers like this: _________________ */
  @Override
  void protected_render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics, Clip clip) {
    Rectangle rectangle = showableConfig.rectangle;
    FontMetrics fontMetrics = graphics.getFontMetrics();
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    DateTimeFormatter dateTimeFormatter = null;
    if (clip.min() instanceof DateTime) {
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(RationalScalar.of(20, rectangle.height)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int y_pos = (int) showableConfig.y_pos(dateTime);
        navigableMap.put(y_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      Scalar plotHeight = RealScalar.of(rectangle.height - 1);
      int fontSize = fontMetrics.getAscent();
      Scalar dY = StaticHelper.getDecimalStep(clip.width().divide(plotHeight).multiply(RealScalar.of(fontSize * 2)));
      for ( //
          Scalar yValue = Ceiling.toMultipleOf(dY).apply(clip.min()); //
          Scalars.lessEquals(yValue, clip.max()); //
          yValue = yValue.add(dY)) {
        int y_pos = (int) showableConfig.y_pos(yValue);
        navigableMap.put(y_pos, yValue);
      }
    }
    if (gridLines) {
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(rectangle.x, piy, rectangle.x + rectangle.width - 1, piy);
    }
    if (ticks) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine(point.x, point.y, point.x, point.y + length - 1);
        for (int piy : navigableMap.keySet())
          graphics.drawLine(point.x - 2, piy, point.x - 1, piy);
      }
      {
        graphics.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          int piy = entry.getKey();
          Scalar value = entry.getValue();
          String yLabel = Objects.isNull(dateTimeFormatter) //
              ? StaticHelper.format(value)
              : ((DateTime) value).format(dateTimeFormatter);
          graphics.drawString(yLabel, //
              point.x - fontMetrics.stringWidth(yLabel) - 5, //
              piy + fontMetrics.getAscent() / 2 - 1);
        }
      }
    }
  }
}
