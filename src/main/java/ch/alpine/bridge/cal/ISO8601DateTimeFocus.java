// code by jph
package ch.alpine.bridge.cal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/** uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS */
public enum ISO8601DateTimeFocus implements DateTimeFocus {
  INSTANCE;

  @Override
  public DateTimeFormatter focus(ChronoUnit chronoUnit) {
    switch (chronoUnit) {
    case NANOS:
      return DateTimeFormatter.ofPattern("ss.SSSSSSSSS", Locale.US);
    case MICROS:
      return DateTimeFormatter.ofPattern("ss.SSSSSS", Locale.US);
    case MILLIS:
      return DateTimeFormatter.ofPattern("ss.SSS", Locale.US);
    case SECONDS:
      return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
    case MINUTES:
      return DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    case HOURS:
      return DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    case DAYS:
      return DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
    case MONTHS:
      return DateTimeFormatter.ofPattern("uuuu-MM", Locale.US);
    case YEARS:
      return DateTimeFormatter.ofPattern("uuuu", Locale.US);
    default:
      throw new IllegalArgumentException(chronoUnit.toString());
    }
  }
}
