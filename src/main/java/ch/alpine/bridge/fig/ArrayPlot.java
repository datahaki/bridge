// code by jph
package ch.alpine.bridge.fig;

import java.awt.image.AffineTransformOp;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public class ArrayPlot extends AbstractGridPlot {
  /** @param matrix
   * @param cbb
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    ScalableImage scalableImage = new ScalableImage( //
        ImageFormat.of(rescale.result().map(colorDataGradient)), AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    Clip clip = rescale.clip();
    return new ArrayPlot(colorDataGradient, scalableImage, cbb, clip);
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient) {
    return of(matrix, CoordinateBoundingBox.of( //
        AbstractGridPlot.CENTER_INT.apply(Clips.positive(Unprotect.dimension1(matrix))), //
        AbstractGridPlot.CENTER_INT.apply(Clips.positive(matrix.length()))), //
        colorDataGradient);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix) {
    return of(matrix, ColorDataGradients.GRAYSCALE_REVERSED);
  }

  // ---
  private ArrayPlot(ScalarTensorFunction colorDataGradient, ScalableImage scalableImage, CoordinateBoundingBox cbb, Clip clip) {
    super(colorDataGradient, scalableImage, cbb, clip);
  }
}
