// code by jph
package sys.mat;

import java.util.IntSummaryStatistics;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Denominator;
import ch.alpine.tensor.num.Numerator;

/** denominator is allowed to be zero */
public record Ratio(int num, int den) {
  public static Ratio of(Scalar scalar) {
    return new Ratio( //
        Numerator.intValueExact(scalar), //
        Denominator.intValueExact(scalar));
  }

  public static Ratio of(IntSummaryStatistics intSummaryStatistics) {
    return new Ratio( //
        (int) intSummaryStatistics.getSum(), //
        (int) intSummaryStatistics.getCount());
  }

  // ---
  public Ratio mulBoth(int factor) {
    return new Ratio( //
        Math.multiplyExact(num, factor), //
        Math.multiplyExact(den, factor));
  }

  public Ratio divBoth(int factor) {
    return new Ratio( //
        IntegerMath.divideExact(num, factor), //
        IntegerMath.divideExact(den, factor));
  }

  public Scalar toFraction() {
    return RationalScalar.of(num, den);
  }

  public double toDouble() {
    return num / (double) den;
  }

  @Override
  public String toString() { // used for music meter
    return num + "/" + den;
  }
}
