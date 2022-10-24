// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public class ArrayPlot extends BaseShowable {
  private static final ScalarUnaryOperator MATHEMATICA = s -> RealScalar.ONE.subtract(s).multiply(RealScalar.of(255));

  public static Showable of(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    return new ArrayPlot(bufferedImage, cbb);
  }

  public static Showable of(Tensor matrix) {
    return of(ImageFormat.of(Rescale.of(matrix).map(MATHEMATICA)), //
        CoordinateBoundingBox.of( //
            Clips.interval(0, 1), Clips.interval(0, 1)));
  }

  // ---
  /** @param visualImage
   * @return */
  private final BufferedImage bufferedImage;
  private final CoordinateBoundingBox cbb;

  private ArrayPlot(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    this.bufferedImage = bufferedImage;
    this.cbb = cbb;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics _g) {
    Point2D.Double ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).min(), //
        cbb.getClip(1).max()));
    Point2D.Double dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).max(), //
        cbb.getClip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    _g.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), //
        (int) ul.getX(), //
        (int) ul.getY(), null);
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }
}
