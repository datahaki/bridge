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

import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clip;

// TODO BRIDGE logarithmic scale
class AxisX extends Axis {
  public AxisX(ShowOptions showOptions) {
    super(showOptions);
  }

  @Override
  protected void render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics) {
    Clip clip = showableConfig.xRange;
    Rectangle rectangle = showableConfig.rectangle;
    graphics.setFont(getFont());
    FontMetrics fontMetrics = graphics.getFontMetrics();
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    DateTimeFormatter dateTimeFormatter = null;
    if (clip.min() instanceof DateTime) {
      // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(Rational.of(100, rectangle.width)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = showOptions.dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int x_pos = (int) showableConfig.x_pos(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else
      Ticks.stream(clip, Axis.RESERVE.divide(RealScalar.of(rectangle.width))) //
          .forEach(tick -> navigableMap.put((int) showableConfig.x_pos(tick), tick));
    if (showOptions.contains(ShowOption.GRID)) { // grid lines |
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, rectangle.y + rectangle.height);
    }
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
      for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
        Scalar value = entry.getValue();
        String xLabel = Objects.isNull(dateTimeFormatter) //
            ? Ticks.format(value)
            : ((DateTime) value).format(dateTimeFormatter);
        graphics.drawString(xLabel, //
            entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
            point.y + 3 + fontMetrics.getAscent());
      }
    }
  }
}
