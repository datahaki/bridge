// code by jph
package ch.alpine.bridge.lang;

import java.math.BigInteger;
import java.util.Optional;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;

public enum PrettyScalar {
  ;
  private static final char SPACE = '\u2009';

  public static String of(BigInteger bigInteger) {
    String string = bigInteger.abs().toString();
    int offset = string.length() % 3;
    if (offset == 0)
      offset = 3;
    StringBuilder stringBuilder = new StringBuilder();
    if (bigInteger.signum() < 0)
      stringBuilder.append('-');
    stringBuilder.append(string.substring(0, offset));
    for (int index = offset; index < string.length(); index += 3) {
      stringBuilder.append(SPACE);
      stringBuilder.append(string.substring(index, index + 3));
    }
    return stringBuilder.toString();
  }

  public static String of(Scalar scalar) {
    Optional<BigInteger> optional = Scalars.optionalBigInteger(scalar);
    if (optional.isPresent())
      return of(optional.orElseThrow());
    if (scalar instanceof Quantity quantity)
      return of(quantity.value()) + " " + PrettyUnit.of(quantity.unit());
    if (scalar instanceof RationalScalar rationalScalar)
      return of(rationalScalar.numerator()) + " / " + of(rationalScalar.denominator());
    if (scalar instanceof DoubleScalar doubleScalar) {
      String string = scalar.toString();
      int index = string.indexOf('.');
      BigInteger bigInteger = new BigInteger(string.substring(0, index));
      return of(bigInteger) + string.substring(index);
    }
    return scalar.toString();
  }
}
