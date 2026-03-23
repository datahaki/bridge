// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.bridge.fig.BarLegend;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPlot.html">MatrixPlot</a> */
public enum MatrixPlot {
  ;
  private static final boolean SYMMETRIZE_DEFAULT = false;

  /** @param matrix
   * @param colorDataGradient
   * @param symmetrize interval about zero
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient, boolean symmetrize) {
    if (matrix.length() == 0)
      return EmptyPlot.INSTANCE;
    MatrixQ.require(matrix);
    Clip clip = Flatten.scalars(matrix) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
    if (Objects.nonNull(clip) && symmetrize) {
      if (clip.min() instanceof DateTime)
        System.err.println("bypass symmetrize");
      else
        clip = Clips.symmetrize(clip);
    }
    Rescale rescale = new Rescale(matrix, clip);
    BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
    return ImagePlot.of(bufferedImage, ImageResize.DEGREE_0, //
        StaticHelper.shift(matrix), //
        new BarLegend(rescale.clip(), colorDataGradient), //
        true, RealScalar.ONE);
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient) {
    return of(matrix, colorDataGradient, SYMMETRIZE_DEFAULT);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix) {
    return of(matrix, SYMMETRIZE_DEFAULT);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix, boolean symmetrize) {
    return of(matrix, symmetrize //
        ? ColorDataGradients.TEMPERATURE_LIGHT
        : ColorDataGradients.JET, symmetrize);
  }
}
