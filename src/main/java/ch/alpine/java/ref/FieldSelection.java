// code by jph
package ch.alpine.java.ref;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSelection {
  /** @return */
  String list();
  // TODO also allow @FieldSelection(list="{/dev/ttyS0, /dev/ttyS1, /dev/ttyS2, /dev/ttyS3}")
  // for string field
}
