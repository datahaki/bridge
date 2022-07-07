// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.util.InvalidFieldDetection;

/** introduces options for quick selection
 * 
 * the options are computed at runtime and may depend on the current
 * state of the object, or software.
 * 
 * The method should be annotated with {@link ReflectionMarker}.
 * 
 * @see InvalidFieldDetection */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSelectionCallback {
  /** @return name of function that returns a list of objects,
   * for instance List<Object>, List<String>, ... */
  String value();
}
