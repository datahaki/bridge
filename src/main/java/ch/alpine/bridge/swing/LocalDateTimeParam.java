// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDateTime;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class LocalDateTimeParam {
  public final LocalDateParam date;
  public final LocalTimeParam time;

  public LocalDateTimeParam(LocalDateTime localDateTime) {
    date = new LocalDateParam(localDateTime.toLocalDate());
    time = new LocalTimeParam(localDateTime.toLocalTime());
  }

  public void set(LocalDateTime localDateTime) {
    date.set(localDateTime.toLocalDate());
    time.set(localDateTime.toLocalTime());
  }

  public LocalDateTime toLocalDateTime() {
    return LocalDateTime.of(date.toLocalDate(), time.toLocalTime()); //
  }

  @Override
  public String toString() {
    return toLocalDateTime().toString();
  }
}
