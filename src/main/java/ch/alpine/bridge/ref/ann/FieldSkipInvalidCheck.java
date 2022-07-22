// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;

/** @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSkipInvalidCheck {
  // ---
}
