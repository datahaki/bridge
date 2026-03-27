// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

enum StaticHelper {
  ;
  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;
  public static final int TICK = 3;

  private static Scalar delta(Scalar scalar) {
    // invoking scalar.zero() is needed when scalar is instance of DateTime
    Unit unit = QuantityUnit.of(scalar.zero());
    return Quantity.of(scalar.one(), unit);
  }

  public static Clip nonZero(Clip clip) {
    return clip.isNonDegenerate() //
        ? clip
        : Clips.centered(clip.min(), delta(clip.min()));
  }

  public static CoordinateBoundingBox nonZero(CoordinateBoundingBox cbb) {
    return CoordinateBoundingBox.of(cbb.stream().map(StaticHelper::nonZero));
  }
}
