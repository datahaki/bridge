// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.alpine.bridge.col.HueFromColor;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.MatrixGradient;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public record ReliefImage(BufferedImage bufferedImage, CoordinateBoundingBox cbb, Clip clip, ScalarTensorFunction colorDataGradient) {
  public static Tensor REF = Tensors.vector(-0.31622776601683794, 0.9486832980505138);

  public static ReliefImage of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
    MatrixGradient matrixGradient = MatrixGradient.of(matrix);
    List<Integer> list = Dimensions.of(matrix);
    Scalar h0 = RealScalar.of(list.get(0) - 1).divide(cbb.clip(0).width());
    Scalar h1 = RealScalar.of(list.get(1) - 1).divide(cbb.clip(1).width());
    matrixGradient = matrixGradient.rescale(h0, h1).rescale();
    // TODO use raster underlying BufImg
    for (int i = 0; i < bufferedImage.getWidth(); ++i)
      for (int j = 0; j < bufferedImage.getHeight(); ++j) {
        int rgb = bufferedImage.getRGB(i, j);
        HueFromColor hueFromColor = HueFromColor.of(new Color(rgb));
        Tensor nrm = matrixGradient.Cross(j, i);
        Scalar dot = (Scalar) nrm.dot(REF);
        Scalar s1 = Clips.unit().apply(RealScalar.ONE.subtract(dot));
        Scalar s2 = Clips.unit().apply(RealScalar.ONE.add(dot));
        Color modifHSV = hueFromColor.modifHSV(s1.number().doubleValue(), s2.number().doubleValue());
        bufferedImage.setRGB(i, j, modifHSV.getRGB());
      }
    return new ReliefImage(bufferedImage, cbb, rescale.clip(), colorDataGradient);
  }
}
