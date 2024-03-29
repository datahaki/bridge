// code by jph
package ch.alpine.bridge.cal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/** uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS */
// TODO BRIDGE usages no good, instead determine resolution dynamically
public enum ISO8601DateTimeFocus implements DateTimeFocus {
  INSTANCE;

  @Override
  public DateTimeFormatter focus(ChronoUnit chronoUnit) {
    return switch (chronoUnit) {
    case NANOS -> DateTimeFormatter.ofPattern("ss.SSSSSSSSS", Locale.US);
    case MICROS -> DateTimeFormatter.ofPattern("ss.SSSSSS", Locale.US);
    case MILLIS -> DateTimeFormatter.ofPattern("ss.SSS", Locale.US);
    case SECONDS -> DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
    case MINUTES -> DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    case HOURS -> DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    case HALF_DAYS -> DateTimeFormatter.ofPattern("MM-dd HH", Locale.US);
    case DAYS -> DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
    case WEEKS -> DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
    case MONTHS -> DateTimeFormatter.ofPattern("uuuu-MM", Locale.US);
    case YEARS -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    case DECADES -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    case CENTURIES -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    case MILLENNIA -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    case ERAS -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    case FOREVER -> DateTimeFormatter.ofPattern("uuuu", Locale.US);
    default -> throw new IllegalArgumentException(chronoUnit.toString());
    };
  }
}
