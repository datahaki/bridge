// code by jph
package ch.alpine.bridge.ref;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.ResourceData;

public class ParamContainerExt extends ParamContainer {
  public static final ParamContainerExt INSTANCE_EXT = //
      ObjectProperties.set(new ParamContainerExt(), ResourceData.properties("/io/ParamContainerExt.properties"));
  // ---
  public Tensor onlyInExt = Tensors.vector(1, 2, 3);
  @SuppressWarnings("unused")
  private Scalar _private;

  public ParamContainerExt() {
    string = "fromConstructor";
  }
}
