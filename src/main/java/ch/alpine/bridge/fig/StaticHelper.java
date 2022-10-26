// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/* package */ enum StaticHelper {
  ;
  /** @param vector
   * @return null if given vector does not contain finite scalars */
  public static Clip minMax(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
  }

  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;

  private static Scalar delta(Scalar scalar) {
    Unit unit = QuantityUnit.of(scalar.zero());
    return Quantity.of(scalar.one(), unit);
  }

  public static Clip nonZero(Clip clip) {
    if (Scalars.isZero(clip.width())) {
      Scalar delta = delta(clip.min());
      return Clips.interval(clip.min().subtract(delta), clip.max().add(delta));
    }
    return clip;
  }

  public static CoordinateBoundingBox nonZero(CoordinateBoundingBox cbb) {
    return CoordinateBoundingBox.of(cbb.stream().map(StaticHelper::nonZero));
  }
}
