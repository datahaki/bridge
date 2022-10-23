// code by jph
package ch.alpine.bridge.cal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@FunctionalInterface
public interface DateTimeFocus {
  /** @param chronoUnit
   * @return DateTimeFormatter with resolution based on given chrono unit */
  DateTimeFormatter focus(ChronoUnit chronoUnit);
}
