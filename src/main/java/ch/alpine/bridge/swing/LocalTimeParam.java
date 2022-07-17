// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.Round;

@ReflectionMarker
public class LocalTimeParam {
  private static final Scalar NANOS = RealScalar.of(1_000_000_000);
  @FieldInteger
  @FieldClip(min = "0", max = "23")
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

  @ReflectionMarker
  public List<Scalar> sixty() {
    return Range.of(0, 12).multiply(RealScalar.of(5)).stream() //
        .map(Scalar.class::cast) //
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

  @Override
  public String toString() {
    return toLocalTime().toString();
  }
}
