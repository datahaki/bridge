// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.Optional;
import java.util.stream.Stream;

import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TsEntry;

/* package */ enum StaticHelper {
  ;
  /** @param vector
   * @return null if given vector does not contain finite scalars */
  public static Clip minMax(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
  }

  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;

  private static Scalar delta(Scalar scalar) {
    return Quantity.of(scalar.one(), QuantityUnit.of(scalar.zero()));
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
    return Unicode.valueOf(scalar);
  }

  private static final Scalar[] RATIOS = { //
      RationalScalar.of(1, 5), //
      RationalScalar.of(1, 2) };

  /** @param scalar positive
   * @return */
  public static Scalar getDecimalStep(Scalar scalar) {
    Sign.requirePositive(scalar);
    Scalar decStep = Quantity.of( //
        Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(Unprotect.withoutUnit(scalar)))), //
        QuantityUnit.of(scalar));
    return Stream.of(RATIOS) //
        .map(decStep::multiply) //
        .filter(value -> Scalars.lessEquals(scalar, value)) //
        .findFirst() //
        .orElse(decStep);
  }

  public static Optional<CoordinateBoundingBox> fullPlotRange(TimeSeries timeSeries, TensorScalarFunction tsf) {
    return timeSeries.isEmpty() //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            timeSeries.domain(), //
            timeSeries.stream().map(TsEntry::value).map(tsf).collect(MinMax.toClip())));
  }

  public static Color withAlpha(Color color, int alpha) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
  }
}
