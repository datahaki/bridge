// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.util.Arrays;

import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.chq.IntegerQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/* package */ enum StaticHelper {
  ;
  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;

  private static Scalar delta(Scalar scalar) {
    // invoking scalar.zero() is needed when scalar is instance of DateTime
    Unit unit = QuantityUnit.of(scalar.zero());
    return Quantity.of(scalar.one(), unit);
  }

  public static Clip nonZero(Clip clip) {
    return Scalars.isZero(clip.width()) //
        ? Clips.centered(clip.min(), delta(clip.min()))
        : clip;
  }

  public static CoordinateBoundingBox nonZero(CoordinateBoundingBox cbb) {
    return CoordinateBoundingBox.of(cbb.stream().map(StaticHelper::nonZero));
  }

  public static String format(Scalar value) {
    Scalar display = Unprotect.withoutUnit(value);
    Scalar scalar = IntegerQ.of(display) //
        ? display
        : N.DOUBLE.apply(display);
    return UnicodeString.of(scalar);
  }

  private static final Scalar[] RATIOS = { //
      Rational.of(1, 5), //
      Rational.of(1, 2) };

  /** @param scalar positive
   * @return */
  public static Scalar getDecimalStep(Scalar scalar) {
    Sign.requirePositive(scalar);
    Scalar decStep = Quantity.of( //
        Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(Unprotect.withoutUnit(scalar)))), //
        QuantityUnit.of(scalar));
    return Arrays.stream(RATIOS) //
        .map(decStep::multiply) //
        .filter(value -> Scalars.lessEquals(scalar, value)) //
        .findFirst() //
        .orElse(decStep);
  }

  public static int interval(FontMetrics fontMetrics) {
    return fontMetrics.getAscent() * 8 / 5;
  }
}
