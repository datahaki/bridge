// code by jph
package ch.alpine.bridge.fig;

import java.awt.Image;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPlot.html">MatrixPlot</a> */
public class MatrixPlot extends AbstractGridPlot {
  /** @param matrix
   * @param colorDataGradient
   * @param symmetrize
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient, boolean symmetrize) {
    MatrixQ.require(matrix);
    Clip clip = Flatten.scalars(matrix) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
    if (Objects.nonNull(clip) && symmetrize) {
      if (clip.min() instanceof DateTime)
        System.err.println("bypass symmetrize");
      else
        clip = Clips.absolute(Max.of(clip.min().negate(), clip.max()));
    }
    Rescale rescale = new Rescale(matrix, clip);
    ScalableImage scalableImage = new ScalableImage( //
        ImageFormat.of(rescale.result().map(colorDataGradient)), Image.SCALE_AREA_AVERAGING);
    CoordinateBoundingBox cbb = CoordinateBoundingBox.of( //
        StaticHelper.TRANSLATION.apply(Clips.positive(Unprotect.dimension1(matrix))), //
        StaticHelper.TRANSLATION.apply(Clips.positive(matrix.length())));
    return new MatrixPlot(colorDataGradient, scalableImage, cbb, rescale.clip());
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient) {
    return of(matrix, colorDataGradient, true);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix) {
    return of(matrix, ColorDataGradients.TEMPERATURE_LIGHT);
  }

  // ---
  private MatrixPlot(ScalarTensorFunction colorDataGradient, ScalableImage scalableImage, CoordinateBoundingBox cbb, Clip clip) {
    super(colorDataGradient, scalableImage, cbb, clip);
  }

  @Override // from Showable
  public Optional<Scalar> aspectRatioHint() {
    return Optional.of(RealScalar.ONE);
  }
}
