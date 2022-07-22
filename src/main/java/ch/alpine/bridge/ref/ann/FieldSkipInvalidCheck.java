// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;

/** marks a fields to be ignored in the invalid field detection test
 * 
 * @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSkipInvalidCheck {
  // ---
}
