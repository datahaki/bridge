// code by jph
package ch.alpine.bridge.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.exp.Log10;

/* contains redundancies with MetricPrefix */
public enum FriendlyFormat {
  ;
  private static final Map<Integer, String> MAP = new HashMap<>();
  static {
    MAP.put(15, "P");
    MAP.put(12, "T");
    MAP.put(9, "G");
    MAP.put(6, "M");
    MAP.put(3, "k");
    MAP.put(0, "");
    MAP.put(-3, "m");
    MAP.put(-6, "u");
    MAP.put(-9, "n");
    MAP.put(-12, "p");
    MAP.put(-15, "f");
  }
  private static final Predicate<String> PREDICATE = //
      Pattern.compile("[%A-Z_a-z]+").asMatchPredicate();

  /** @param scalar
   * @param unit atomic
   * @return */
  public static Scalar of(Scalar scalar, String unit) {
    if (unit.isEmpty() || //
        PREDICATE.test(unit)) {
      Scalar abs = Abs.FUNCTION.apply(scalar);
      Scalar log = Log10.FUNCTION.apply(abs);
      Scalar floor = Floor.toMultipleOf(RealScalar.of(3)).apply(log);
      int exp = Scalars.intValueExact(floor);
      Scalar fallback = Quantity.of(scalar, unit);
      if (MAP.containsKey(exp)) {
        Unit target = Unit.of(MAP.get(exp) + unit);
        if (CompatibleUnitQ.SI().with(target).test(fallback))
          return Round._1.apply(UnitConvert.SI().to(target).apply(fallback));
      }
      return fallback;
    }
    throw new IllegalArgumentException(unit);
  }

  public static Scalar of(Number number, String unit) {
    return of(RealScalar.of(number), unit);
  }

  /** @return -18+4/7 for display, not parsing */
  public static String toHighSchoolString(Scalar scalar) {
    Scalar floor = Floor.FUNCTION.apply(scalar);
    if (Scalars.isZero(floor))
      return scalar.toString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(floor);
    Scalar reman = scalar.subtract(floor);
    if (Scalars.nonZero(reman))
      stringBuilder.append("+" + reman);
    return stringBuilder.toString();
  }
}
