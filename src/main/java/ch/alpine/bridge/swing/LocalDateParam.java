// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class LocalDateParam {
  @FieldInteger
  @FieldClip(min = "1900", max = "3000")
  public Scalar year;
  // ---
  public Month month;
  // ---
  @FieldClip(min = "1", max = "31")
  @FieldSelectionCallback("days")
  public Scalar day;

  public LocalDateParam(LocalDate localDate) {
    year = RealScalar.of(localDate.getYear());
    month = localDate.getMonth();
    day = RealScalar.of(localDate.getDayOfMonth());
  }

  public LocalDate toLocalDate() {
    return LocalDate.of( //
        year.number().intValue(), //
        month, //
        day.number().intValue());//
  }

  @Override
  public String toString() {
    return toLocalDate().toString();
  }

  public List<Scalar> days() {
    int length = month.length(LocalDate.of(year.number().intValue(), 1, 1).isLeapYear());
    return IntStream.range(1, length + 1).mapToObj(RealScalar::of).collect(Collectors.toList());
  }
}
