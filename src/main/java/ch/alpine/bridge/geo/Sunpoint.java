// code by jph
package ch.alpine.bridge.geo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.sca.Mod;
import ch.alpine.tensor.sca.tri.Sin;

class Sunpoint {
  private static final Mod mod = Mod.function(Pi.TWO, Pi.VALUE.negate());

  Sunpoint() {
    LocalDateTime ldt = LocalDateTime.now();
    // FIXME has to be UTC
    IO.println(ldt);
    int solar = ldt.getDayOfYear() - 81;
    Scalar angle = RealScalar.of(360 * solar / 365.25);
    Scalar declination = Degree.of(23.44).multiply(Sin.FUNCTION.apply(angle));
    IO.println(declination);
    LocalTime localTime = ldt.toLocalTime();
    Scalar hours = RealScalar.of(localTime.getHour());
    Scalar min = Rational.of(localTime.getMinute(), 60);
    Scalar sec = Rational.of(localTime.getSecond(), 3600);
    Scalar t_utc = hours.add(min).add(sec);
    Scalar lambda_s = t_utc.subtract(RealScalar.of(12)).multiply(Degree.of(15));
    lambda_s = mod.apply(lambda_s);
    IO.println(lambda_s);
  }

  static void main() {
    new Sunpoint();
  }
}
