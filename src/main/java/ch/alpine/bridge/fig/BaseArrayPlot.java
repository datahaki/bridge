// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** base class for ArrayPlot and MatrixPlot */
/* package */ final class BaseArrayPlot extends ImageShowable {
  private final CoordinateBoundingBox cbb;
  private final ScalableImage scalableImage;
  private final BarLegend barLegend;

  public BaseArrayPlot( //
      ScalableImage scalableImage, //
      CoordinateBoundingBox cbb, //
      BarLegend barLegend) {
    super(cbb);
    this.cbb = cbb;
    this.scalableImage = scalableImage;
    this.barLegend = barLegend;
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

  @Override
  protected BarLegend barLegend() {
    return barLegend;
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
