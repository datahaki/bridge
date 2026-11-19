// code by jph
package ch.alpine.bridge.fig;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.fig.grd.AxisConfig;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** Careful: rectangle width and height have to be greater than 1
 * 
 * @param rectangle
 * @param cbb
 * @param confX
 * @param confY */
public record ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb, AxisConfig confX, AxisConfig confY) {
  public static ShowableConfig empty(Rectangle rectangle) {
    return new ShowableConfig(rectangle, null, null, null);
  }

  public Point2D model2point(Tensor vector) {
    return new Point2D.Double( //
        confX.model2pixel(vector.Get(0)), //
        confY.model2pixel(vector.Get(1)));
  }

  public Optional<Tensor> point2model(Point point) {
    return rectangle.contains(point) //
        ? Optional.of(Tensors.of( //
            confX.pixel2model(point.x), //
            confY.pixel2model(point.y)))
        : Optional.empty();
  }

  public Optional<Tensor> point2range(Point point) {
    return rectangle.contains(point) //
        ? Optional.of(Tensors.of( //
            confX.pixel2range(point.x), //
            confY.pixel2range(point.y)))
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

  public boolean isEmpty() {
    return Objects.isNull(cbb);
  }
}
