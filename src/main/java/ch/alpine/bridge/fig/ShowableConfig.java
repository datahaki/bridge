// code by jph
package ch.alpine.bridge.fig;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** Careful: rectangle width and height have to be greater than 1
 * 
 * @param rectangle
 * @param cbb
 * @param confX
 * @param confY */
public record ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb, ConfBase confX, ConfBase confY) {
  /** @param rectangle
   * @param cbb
   * @return */
  public static ShowableConfig yDecr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfDecr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  /** @param rectangle
   * @param cbb
   * @return */
  public static ShowableConfig yIncr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfIncr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  public Point2D toPoint2D(Tensor vector) {
    return new Point2D.Double( //
        confX.pixel(vector.Get(0)), //
        confY.pixel(vector.Get(1)));
  }

  public Optional<Tensor> toValue(Point point) {
    return rectangle.contains(point) //
        ? Optional.of(Tensors.of( //
            confX.model(point.x), //
            confY.model(point.y)))
        : Optional.empty();
  }

  /** @return */
  public ShowableConfig pruned() {
    return new ShowableConfig( //
        new Rectangle(0, 0, rectangle.width, rectangle.height), //
        cbb, //
        confX.pruned(), //
        confY.pruned());
  }
}
