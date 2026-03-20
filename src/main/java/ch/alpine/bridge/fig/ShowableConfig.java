// code by jph
package ch.alpine.bridge.fig;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public final class ShowableConfig {
  public static ShowableConfig yDecr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfDecr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  public static ShowableConfig yIncr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfIncr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  // ---
  private final Rectangle rectangle;
  private final CoordinateBoundingBox cbb;
  public final ConfBase confX;
  public final ConfBase confY;

  /** Careful: rectangle width and height have to be greater than 1
   * 
   * @param rectangle
   * @param cbb */
  private ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb, ConfBase confX, ConfBase confY) {
    this.rectangle = rectangle;
    this.cbb = cbb;
    this.confX = confX;
    this.confY = confY;
  }

  public Rectangle rectangle() {
    return rectangle;
  }

  public Point2D toPoint2D(Tensor vector) {
    return new Point2D.Double( //
        confX.x_pos(vector.Get(0)), //
        confY.x_pos(vector.Get(1)));
  }

  public Optional<Tensor> toValue(Point point) {
    return rectangle.contains(point) // TODO
        ? Optional.of(Tensors.of( //
            confX.value(point.x), //
            confY.value(point.y)))
        : Optional.empty();
  }

  /** @return may be null */
  public CoordinateBoundingBox getCbb() {
    return cbb;
  }

  public ShowableConfig clipped() {
    return new ShowableConfig( //
        new Rectangle(0, 0, rectangle.width, rectangle.height), cbb, //
        confX.clipped(), //
        confY.clipped());
  }
}
