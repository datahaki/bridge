// code by jph
package ch.alpine.bridge.fig;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.UnitConvert;

/** Careful: rectangle width and height have to be greater than 1
 * 
 * @param rectangle
 * @param cbb
 * @param confX
 * @param confY */
public record ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb, ConfBase confX, ConfBase confY) {
  private static final ScalarUnaryOperator TO_DEGREE = UnitConvert.SI().to(Degree.UNIT);

  public static ShowableConfig empty(Rectangle rectangle) {
    return new ShowableConfig(rectangle, null, null, null);
  }

  /** @param rectangle
   * @param cbb
   * @return */
  public static ShowableConfig yDecr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfLinIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfLinDecr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  /** @param rectangle
   * @param cbb
   * @return */
  public static ShowableConfig yIncr(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        new ConfLinIncr(rectangle.x, rectangle.width, cbb.clip(0)), //
        new ConfLinIncr(rectangle.y, rectangle.height, cbb.clip(1)));
  }

  public static ShowableConfig geo(Rectangle rectangle, CoordinateBoundingBox cbb, TilePixel origin) {
    CoordinateBoundingBox cbb_deg = CoordinateBoundingBox.of(cbb.stream() //
        .map(clip -> Unprotect.warp(clip, TO_DEGREE)));
    return new ShowableConfig(rectangle, cbb_deg, //
        new ConfLinIncr(rectangle.x, rectangle.width, cbb_deg.clip(0)), //
        new ConfLatDecr(rectangle.y, rectangle.height, cbb_deg.clip(1), origin));
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

  public boolean isEmpty() {
    return Objects.isNull(cbb);
  }
}
