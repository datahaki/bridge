// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

@ReflectionMarker
public class ClipParam {
  public Clip clipReal = Clips.interval(2, 3);
  public Clip clipMeter = Clips.absolute(Quantity.of(1, "m"));
}
