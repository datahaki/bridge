// code by jph
package ch.alpine.bridge.lang;

import java.awt.Graphics;
import java.math.BigInteger;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

public enum UnicodeString {
  ;
  private static final char SPACE = ' ';
  private static final String OVER = SPACE + "/" + SPACE;
  private static final char NARROW = '\u2009';

  /** @param scalar
   * @return string expression of given scalar suitable for rendering in {@link Graphics} */
  public static String of(Scalar scalar) {
    return switch (scalar) {
    case Rational rational -> rational.isInteger() //
        ? of(rational.numerator())
        : of(rational.numerator()) + OVER + of(rational.denominator());
    case Quantity quantity -> of(quantity.value()) + SPACE + of(quantity.unit());
    // TODO BRIDGE DecimalScalar
    case DoubleScalar doubleScalar -> {
      String string = doubleScalar.toString();
      int index = string.indexOf('.');
      yield 0 <= index //
          ? of(new BigInteger(string.substring(0, index))) + string.substring(index)
          : string; // Infinity, NaN
    }
    default -> scalar.toString();
    };
  }

  /** @param bigInteger
   * @return string expression of given bigInteger suitable for rendering in {@link Graphics} */
  public static String of(BigInteger bigInteger) {
    String string = bigInteger.abs().toString();
    int offset = string.length() % 3;
    if (offset == 0)
      offset = 3;
    StringBuilder stringBuilder = new StringBuilder();
    if (bigInteger.signum() < 0)
      stringBuilder.append('-');
    stringBuilder.append(string, 0, offset);
    for (int index = offset; index < string.length(); index += 3) {
      stringBuilder.append(NARROW);
      stringBuilder.append(string, index, index + 3);
    }
    return stringBuilder.toString();
  }

  public static String of(Integer integer) {
    return of(BigInteger.valueOf(integer));
  }
  // ---

  /** "m*s^-1" -> "m/s"
   * use of unicode characters for degC, Ohm and micro-x
   * use of unicode characters for exponents such as ^-2
   * etc.
   * 
   * @param unit
   * @return string expression of given unit suitable for rendering in {@link Graphics} */
  public static String of(Unit unit) {
    return UnicodeUnit.toString(unit);
  }
}
