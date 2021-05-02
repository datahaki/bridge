// code by jph
package ch.alpine.java.ref;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** intended for user to set Boolean value to true
 * whereas the software only sets Boolean value to false */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldFuse {
  /** @return */
  String text();
}
