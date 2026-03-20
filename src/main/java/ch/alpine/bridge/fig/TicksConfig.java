// code by jph
package ch.alpine.bridge.fig;

import java.time.format.DateTimeFormatter;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clip;

class TicksConfig {
  final NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
  final DateTimeFormatter dateTimeFormatter;

  public TicksConfig(ConfBase confBase, DateTimeFocus dateTimeFocus) {
    Clip clip = confBase.clip();
    if (clip.min() instanceof DateTime) {
      // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(Rational.of(100, confBase.width)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int x_pos = (int) confBase.pixel(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      Ticks.stream(clip, Axis.RESERVE.divide(RealScalar.of(confBase.width))) //
          .forEach(tick -> navigableMap.put((int) confBase.pixel(tick), tick));
      dateTimeFormatter = null;
    }
  }
}
