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
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;

/** annotation to defined the limits for a field of type {@link Scalar}
 * and {@link Clip}
 * 
 * if the {@link Unit} of min and max are not identical
 * then {@link UnitConvert#SI()} is used for unification.
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

  /** useful only when min and max are specified with
   * different {@link Unit}s. By default, the unit
   * of {@link #min()} is taken for clipping.
   * By overriding the default, the unit of
   * {@link #max()} is taken.
   * 
   * @return */
  boolean useMinUnit() default true;

  boolean integer() default false;
}
