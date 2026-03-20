// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.alpine.bridge.col.HueFromColor;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.img.MatrixGradient;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.nrm.NormalizeUnlessZero;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;

public class ReliefPlot {
  private static final TensorUnaryOperator NORMALIZE_UNLESS_ZERO = NormalizeUnlessZero.with(Vector2Norm::of);
  private static final Tensor REF = NORMALIZE_UNLESS_ZERO.apply(Tensors.vector(-1, 1, 2));

  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
    MatrixGradient matrixGradient = MatrixGradient.of(matrix);
    List<Integer> list = Dimensions.of(matrix);
    Scalar h0 = RealScalar.of(list.get(0) - 1).divide(cbb.clip(0).width());
    Scalar h1 = RealScalar.of(list.get(1) - 1).divide(cbb.clip(1).width());
    matrixGradient = matrixGradient.rescale(h0, h1);
    for (int i = 0; i < bufferedImage.getWidth(); ++i) {
      for (int j = 0; j < bufferedImage.getHeight(); ++j) {
        int rgb = bufferedImage.getRGB(i, j);
        HueFromColor hueFromColor = HueFromColor.of(new Color(rgb));
        Tensor nrm = matrixGradient.get(i, j).append(RealScalar.ONE);
        nrm = NORMALIZE_UNLESS_ZERO.apply(nrm);
        // IO.println(nrm);
        Scalar dot = (Scalar) nrm.dot(REF);
        Scalar s = Clips.unit().apply(RealScalar.ONE.subtract(dot));
        // IO.println(s);
        // s = RealScalar.ONE;
        Color modifHSV = hueFromColor.modifHSV(s.number().doubleValue(), 1);
        bufferedImage.setRGB(i, j, modifHSV.getRGB());
      }
    }
    return ImagePlot.of(bufferedImage, cbb);
  }
}
