// code by gjoel, jph
package ch.alpine.bridge.fig.plt;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
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
    return minMax(vector.stream());
  }

  /** @param stream
   * @return */
  public static Clip minMax(Stream<Tensor> stream) {
    return stream //
        .map(Scalar.class::cast) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
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

  public static Optional<CoordinateBoundingBox> fullPlotRange(TimeSeries timeSeries, TensorScalarFunction tsf) {
    return timeSeries.isEmpty() //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            timeSeries.domain(), //
            timeSeries.stream().map(TsEntry::value).map(tsf).collect(MinMax.toClip())));
  }

  public static Optional<CoordinateBoundingBox> fullPlotRange(TimeSeries timeSeries, TensorUnaryOperator tuo) {
    return timeSeries.isEmpty() //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            timeSeries.domain(), //
            timeSeries.stream().map(TsEntry::value).map(tuo) //
                .flatMap(Tensor::stream) //
                .map(Scalar.class::cast) //
                .collect(MinMax.toClip())));
  }

  public static Clip extend(TimeSeries timeSeries, Clip clip) {
    NavigableSet<Scalar> navigableSet = timeSeries.keySet(timeSeries.domain(), true);
    {
      Scalar lo = navigableSet.floor(clip.min());
      if (Objects.nonNull(lo))
        clip = Clips.interval(lo, clip.max());
    }
    {
      Scalar hi = navigableSet.ceiling(clip.max());
      if (Objects.nonNull(hi))
        clip = Clips.interval(clip.min(), hi);
    }
    return clip;
  }

  private static final UnaryOperator<Clip> SHIFT_HALF = Clips.translation(Rational.HALF.negate());

  protected static CoordinateBoundingBox shift(Tensor matrix) {
    return shift(Unprotect.dimension1(matrix), matrix.length());
  }

  protected static CoordinateBoundingBox shift(BufferedImage bufferedImage) {
    return shift(bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  private static CoordinateBoundingBox shift(int dim0, int dim1) {
    return CoordinateBoundingBox.of( //
        SHIFT_HALF.apply(Clips.positive(dim0)), //
        SHIFT_HALF.apply(Clips.positive(dim1)));
  }
}
