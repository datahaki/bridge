// code by jph, gjoel
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;
import ch.alpine.tensor.Scalar;

/** The field slider is an annotation for a {@link Scalar} field.
 * In order to take effect, the field also has to be annotated with a
 * {@link FieldClip} of finite width.
 * 
 * @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSlider {
  /** @return whether to display the current slider value */
  boolean showValue() default false;

  /** @return whether to show the min and max values of the slider */
  boolean showRange() default false;
}
