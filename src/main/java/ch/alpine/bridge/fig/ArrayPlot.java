// code by jph
package ch.alpine.bridge.fig;

import java.awt.image.BufferedImage;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient, boolean flipY) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
    Clip clip = rescale.clip();
    return ImagePlot.of( //
        bufferedImage, ImageResize.DEGREE_0, //
        cbb, //
        new BarLegend(clip, colorDataGradient), //
        flipY, //
        RealScalar.ONE);
  }

  /** @param matrix
   * @param cbb
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
    Clip clip = rescale.clip();
    return ImagePlot.of( //
        bufferedImage, ImageResize.DEGREE_0, cbb, //
        new BarLegend(clip, colorDataGradient), //
        true, RealScalar.ONE);
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient) {
    return of(matrix, StaticHelper.shift(matrix), colorDataGradient);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix) {
    return of(matrix, ColorDataGradients.GRAYSCALE_REVERSED);
  }
}
