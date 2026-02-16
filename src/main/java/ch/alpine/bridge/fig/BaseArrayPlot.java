// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.UnaryOperator;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** base class for ArrayPlot and MatrixPlot */
/* package */ class BaseArrayPlot extends BarLegendPlot {
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

  // ---
  private final ScalableImage scalableImage;
  private final Clip clip;

  public BaseArrayPlot( //
      ScalarTensorFunction colorDataGradient, //
      ScalableImage scalableImage, //
      CoordinateBoundingBox cbb, //
      Clip clip) {
    super(cbb, colorDataGradient);
    this.scalableImage = scalableImage;
    this.clip = clip;
  }

  @Override // from Showable
  public final void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        cbb.clip(1).min()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        cbb.clip(1).max()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height) {
      graphics.drawImage(scalableImage.getScaledInstance(getImageResize(), width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
    }
  }

  @Override // from BarLegendPlot
  protected final Clip clip() {
    return clip;
  }

  @Override // from Showable
  public final boolean flipYAxis() {
    return true;
  }

  @Override // from Showable
  public final Optional<Scalar> aspectRatioHint() {
    return Optional.of(RealScalar.ONE);
  }
}
