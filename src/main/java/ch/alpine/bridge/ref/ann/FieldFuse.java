// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;

/** intended for user to set Boolean value to true
 * whereas the software only sets Boolean value to false
 * 
 * Typically, the boolean value of the flag is initially
 * false. When the user clicks the gui element, a callback
 * function is invoked, where the flag is checked for true
 * and cleared to false immediately.
 * 
 * Typically, the boolean field is marked as transient
 * to prevent storing it in the true-state.
 * 
 * @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldFuse {
  /** @return text on button */
  String value() default "action";
}
