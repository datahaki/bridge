// code by jph
package ch.alpine.bridge.ref;

import java.time.LocalTime;
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
public class LocalTimeParam {
  @FieldInteger
  @FieldClip(min = "0", max = "23")
  @FieldSelectionCallback("hours")
  public Scalar hh = RealScalar.of(0);
  @FieldInteger
  @FieldClip(min = "0", max = "59")
  @FieldSelectionCallback("sixty")
  public Scalar mm = RealScalar.of(0);
  @FieldInteger
  @FieldClip(min = "0", max = "59")
  @FieldSelectionCallback("sixty")
  public Scalar ss = RealScalar.of(0);

  public List<String> hours() {
    return IntStream.range(0, 24) //
        .mapToObj(Integer::toString) //
        .collect(Collectors.toList());
  }

  public List<String> sixty() {
    return IntStream.range(0, 12) //
        .map(i -> i * 5) //
        .mapToObj(Integer::toString) //
        .collect(Collectors.toList());
  }

  public LocalTime toLocalTime() {
    return LocalTime.of( //
        hh.number().intValue(), //
        mm.number().intValue(), //
        ss.number().intValue());
  }
}
