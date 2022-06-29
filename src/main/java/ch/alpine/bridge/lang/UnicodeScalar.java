// code by jph
package ch.alpine.bridge.lang;

import java.math.BigInteger;
import java.util.Optional;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;

public enum UnicodeScalar {
  ;
  private static final char SPACE = ' ';
  private static final String OVER = SPACE + "/" + SPACE;
  private static final char NARROW = '\u2009';

  /** @param scalar
   * @return */
  public static String of(Scalar scalar) {
    Optional<BigInteger> optional = Scalars.optionalBigInteger(scalar);
    if (optional.isPresent())
      return of(optional.orElseThrow());
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return of(rationalScalar.numerator()) + OVER + of(rationalScalar.denominator());
    }
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return of(quantity.value()) + SPACE + UnicodeUnit.of(quantity.unit());
    }
    if (scalar instanceof DoubleScalar) {
      String string = scalar.toString();
      int index = string.indexOf('.');
      return 0 <= index //
          ? of(new BigInteger(string.substring(0, index))) + string.substring(index)
          : string; // Infinity, NaN
    }
    return scalar.toString();
  }

  private static String of(BigInteger bigInteger) {
    String string = bigInteger.abs().toString();
    int offset = string.length() % 3;
    if (offset == 0)
      offset = 3;
    StringBuilder stringBuilder = new StringBuilder();
    if (bigInteger.signum() < 0)
      stringBuilder.append('-');
    stringBuilder.append(string.substring(0, offset));
    for (int index = offset; index < string.length(); index += 3) {
      stringBuilder.append(NARROW);
      stringBuilder.append(string.substring(index, index + 3));
    }
    return stringBuilder.toString();
  }
}
