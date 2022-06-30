// code by jph
package ch.alpine.bridge.lang;

import java.awt.Graphics;
import java.math.BigInteger;
import java.util.Optional;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

public class Unicode {
  private Unicode() {
    // ---
  }

  private static final char SPACE = ' ';
  private static final String OVER = SPACE + "/" + SPACE;
  private static final char NARROW = '\u2009';

  /** @param scalar
   * @return string expression of given scalar suitable for rendering in {@link Graphics} */
  public static String valueOf(Scalar scalar) {
    Optional<BigInteger> optional = Scalars.optionalBigInteger(scalar);
    if (optional.isPresent())
      return valueOf(optional.orElseThrow());
    if (scalar instanceof RationalScalar rationalScalar)
      return valueOf(rationalScalar.numerator()) + OVER + valueOf(rationalScalar.denominator());
    if (scalar instanceof Quantity quantity)
      return valueOf(quantity.value()) + SPACE + valueOf(quantity.unit());
    if (scalar instanceof DoubleScalar doubleScalar) {
      String string = scalar.toString();
      int index = string.indexOf('.');
      return 0 <= index //
          ? valueOf(new BigInteger(string.substring(0, index))) + string.substring(index)
          : string; // Infinity, NaN
    }
    return scalar.toString();
  }

  /** @param bigInteger
   * @return string expression of given bigInteger suitable for rendering in {@link Graphics} */
  public static String valueOf(BigInteger bigInteger) {
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
  // ---

  /** "m*s^-1" -> "m/s"
   * use of unicode characters for degC, Ohm and micro-x
   * use of unicode characters for exponents such as ^-2
   * etc.
   * 
   * @param unit
   * @return string expression of given unit suitable for rendering in {@link Graphics} */
  public static String valueOf(Unit unit) {
    return UnicodeUnit.toString(unit);
  }
}
