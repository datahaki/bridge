// code by jph
package ch.alpine.bridge.swing;

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
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.Round;

@ReflectionMarker
public class LocalTimeParam {
  private static final Scalar NANOS = RealScalar.of(1_000_000_000);
  @FieldInteger
  @FieldClip(min = "0", max = "23")
  @FieldSelectionCallback("hours")
  public Scalar h;
  // ---
  @FieldInteger
  @FieldClip(min = "0", max = "59")
  @FieldSelectionCallback("sixty")
  public Scalar m;
  // ---
  @FieldClip(min = "0", max = "59.999999999")
  @FieldSelectionCallback("sixty")
  public Scalar s;

  public LocalTimeParam(LocalTime localTime) {
    h = RealScalar.of(localTime.getHour());
    m = RealScalar.of(localTime.getMinute());
    s = RealScalar.of(localTime.getSecond());
    int nano = localTime.getNano();
    if (nano != 0)
      s = Round._9.apply(s.add(RealScalar.of(nano).divide(NANOS)));
  }

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
    Scalar fs = Floor.FUNCTION.apply(s);
    Scalar n = s.subtract(fs).multiply(NANOS);
    return LocalTime.of( //
        h.number().intValue(), //
        m.number().intValue(), //
        fs.number().intValue(), //
        n.number().intValue());
  }
}
