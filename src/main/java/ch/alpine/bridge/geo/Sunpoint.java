// code by jph
package ch.alpine.bridge.geo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.chq.DeterminateScalarQ;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Mod;
import ch.alpine.tensor.sca.tri.ArcCos;
import ch.alpine.tensor.sca.tri.ArcTan;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.sca.tri.Tan;

public record Sunpoint(Scalar lambda_s, Scalar delta) implements Serializable {
  private static final Mod MOD_LON = Mod.function(Pi.TWO, Pi.VALUE.negate());
  private static final Mod MOD_LAT = Mod.function(Pi.VALUE, Pi.HALF.negate());
  private static final Scalar RAD_PER_SEC = UnitSystem.SI().apply(Quantity.of(-15, "deg*h^-1"));
  private static final Scalar AXIAL_TILT = Degree.of(23.44);

  public static Sunpoint now() {
    return of(LocalDateTime.now(ZoneOffset.UTC));
  }

  /** @param localDateTime should be in UTC !
   * @return */
  public static Sunpoint of(LocalDateTime localDateTime) {
    int solar = localDateTime.getDayOfYear() - 81;
    Scalar angle = RealScalar.of(360 / 365.25 * solar);
    LocalDate localDate = localDateTime.toLocalDate();
    LocalTime localTime = localDateTime.toLocalTime();
    Scalar diff = DateTime.of(localDate, localTime).subtract(DateTime.of(localDate, LocalTime.NOON));
    return new Sunpoint( //
        MOD_LON.apply(diff.multiply(RAD_PER_SEC)), //
        AXIAL_TILT.multiply(Sin.FUNCTION.apply(angle)));
  }

  public Scalar fromLongitude(Scalar lambda) {
    Scalar x = Tan.FUNCTION.apply(delta);
    Scalar y = Cos.FUNCTION.apply(lambda.subtract(lambda_s)).negate();
    Scalar result = MOD_LAT.apply(ArcTan.of(x, y));
    Scalar expect = ArcTan.FUNCTION.apply(y.divide(x));
    Tolerance.CHOP.requireClose(result, expect);
    return result;
  }

  public Tensor fromLatitude(Scalar phi) {
    Scalar tan_delta = Tan.FUNCTION.apply(delta);
    Scalar dl = ArcCos.FUNCTION.apply(Tan.FUNCTION.apply(phi).multiply(tan_delta).negate());
    boolean good = DeterminateScalarQ.of(dl) && dl instanceof RealScalar;
    if (good) {
      Scalar l1 = lambda_s.add(dl);
      Scalar l2 = lambda_s.subtract(dl);
      return Tensors.of(l1, l2);
    }
    return Tensors.unmodifiableEmpty();
  }
}
