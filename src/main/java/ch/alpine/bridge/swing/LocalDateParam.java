// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class LocalDateParam {
  @FieldClip(min = "1900", max = "3000")
  @FieldSelectionCallback("years")
  public Integer year;
  // ---
  public Month month;
  // ---
  @FieldClip(min = "1", max = "31")
  @FieldSelectionCallback("days")
  public Integer day;

  public LocalDateParam(LocalDate localDate) {
    set(localDate);
  }

  public void set(LocalDate localDate) {
    year = localDate.getYear();
    month = localDate.getMonth();
    day = localDate.getDayOfMonth();
  }

  public LocalDate toLocalDate() {
    return LocalDate.of( //
        year, //
        month, //
        Math.min(day, maxDays()));//
  }

  @ReflectionMarker
  public List<Integer> years() {
    return IntStream.rangeClosed(-3, 3).map(i -> year + i).boxed().collect(Collectors.toList());
  }

  @ReflectionMarker
  public List<Scalar> days() {
    return IntStream.rangeClosed(1, maxDays()).mapToObj(RealScalar::of).collect(Collectors.toList());
  }

  /** @return maximum number of days in given month and year */
  private int maxDays() {
    return month.length(LocalDate.of(year, 1, 1).isLeapYear());
  }

  @Override
  public String toString() {
    return toLocalDate().toString();
  }
}
