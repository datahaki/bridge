package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

/* package */ abstract class AbstractGridPlot extends BarLegendPlot {
  private final ScalableImage scalableImage;
  private final CoordinateBoundingBox cbb;
  private final Clip clip;
  private Color meshColor = null;

  protected AbstractGridPlot(ScalarTensorFunction colorDataGradient, ScalableImage scalableImage, CoordinateBoundingBox cbb, Clip clip) {
    super(colorDataGradient);
    this.scalableImage = scalableImage;
    this.cbb = cbb;
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
      graphics.drawImage(scalableImage.getScaledInstance(width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
      if (Objects.nonNull(meshColor)) {
        // TODO BRIDGE
      }
    }
  }

  @Override // from Showable
  public final Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  @Override // from BarLegendPlot
  protected final Clip clip() {
    return clip;
  }

  @Override // from Showable
  public final boolean flipYAxis() {
    return true;
  }

  public final void setMeshColor(Color color) {
    meshColor = color;
  }

  public final Color getMeshColor() {
    return meshColor;
  }
}
