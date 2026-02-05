// code by jph
package showcase.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class TimeParam {
  public LocalDateTime dateTime = LocalDateTime.now();
  public LocalDate date = LocalDate.now();
  public LocalTime time = LocalTime.now();
}
