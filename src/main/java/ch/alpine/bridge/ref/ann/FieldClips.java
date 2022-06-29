// code by jph
package ch.alpine.bridge.ref.ann;

import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** interprets specification in {@link FieldClip} to {@link Clip} */
public enum FieldClips {
  ;
  /** @param fieldClip
   * @return clip with min and max {@link Quantity}
   * @throws Exception if parsing of strings to scalars fails
   * @throws Exception if units of min and max are different */
  public static Clip of(FieldClip fieldClip) {
    return Clips.interval( //
        Scalars.fromString(fieldClip.min()), //
        Scalars.fromString(fieldClip.max()));
  }
}
