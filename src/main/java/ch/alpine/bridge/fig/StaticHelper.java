// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;

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

  static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  static final Stroke STROKE_SOLID = new BasicStroke();
  static final int GAP = 5;
}
