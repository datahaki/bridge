// code by jph, gjoel
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class SliderFailParam {
  @FieldSlider
  public Scalar sliderNoClip = RealScalar.ONE;
}
