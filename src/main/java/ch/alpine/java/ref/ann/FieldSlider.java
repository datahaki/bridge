// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** The field slider is an annotation for a scalar field.
 * In order to take effect, the field also has to be annotated with a
 * {@link FieldClip} of finite width. */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSlider {
  // ---
}
