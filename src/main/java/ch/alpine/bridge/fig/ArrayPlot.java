// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.UnaryOperator;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public class ArrayPlot extends BaseShowable {
  private static final ScalarUnaryOperator MATHEMATICA = //
      s -> RealScalar.ONE.subtract(s).multiply(RealScalar.of(255));
  private static final UnaryOperator<Clip> TRANSLATION = Clips.translation(RationalScalar.HALF.negate());

  public static Showable of(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    return new ArrayPlot(bufferedImage, cbb);
  }

  public static Showable of(Tensor matrix) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    rescale.clip();
    return of(ImageFormat.of(rescale.result().map(MATHEMATICA)), //
        CoordinateBoundingBox.of( //
            TRANSLATION.apply(Clips.positive(Unprotect.dimension1(matrix))), //
            TRANSLATION.apply(Clips.positive(matrix.length()))));
  }

  // ---
  private final BufferedImage bufferedImage;
  private final CoordinateBoundingBox cbb;

  private ArrayPlot(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    this.bufferedImage = bufferedImage;
    this.cbb = cbb;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics _g) {
    Point2D.Double ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).min(), //
        cbb.getClip(1).max()));
    Point2D.Double dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).max(), //
        cbb.getClip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height)
      _g.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }
}
