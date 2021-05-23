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
  /** Works on string, tensor, scalar, ...
   * 
   * Example return values:
   * "/dev/tty0|/dev/tty1|/dev/ttyUSB0"
   * "1[%]|2[%]|3[%]"
   * 
   * @return */
  String list();
}
