// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;

import ch.alpine.bridge.ref.ann.FieldLabel;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.num.Pi;

@ReflectionMarker
public class EmptyParam {
  @FieldLabel("select button background")
  public final Color selectColor = new Color(128, 255, 0);
  public final Pivots pivot = Pivots.ARGMAX_ABS;
  public final Scalar scalar = Pi.VALUE;
  public final Tensor tensor = Tensors.empty();
  public final String string = "text";
  public final Boolean flag = true;
}
