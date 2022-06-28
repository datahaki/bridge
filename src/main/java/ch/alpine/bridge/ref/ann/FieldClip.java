// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Unit;

/** annotation to defined the limits for a field of type {@link Scalar}
 * 
 * the {@link Unit} of min and max must be identical
 * 
 * @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldClip {
  /** @return permitted lower bound */
  String min();

  /** @return permitted upper bound */
  String max();
}
