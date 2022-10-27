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
    return Quantity.of(scalar.one(), QuantityUnit.of(scalar.zero()));
  }

  public static Clip nonZero(Clip clip) {
    return Scalars.isZero(clip.width()) //
        ? Clips.centered(clip.min(), delta(clip.min()))
        : clip;
  }

  public static CoordinateBoundingBox nonZero(CoordinateBoundingBox cbb) {
    return CoordinateBoundingBox.of(cbb.stream().map(StaticHelper::nonZero));
  }
}
