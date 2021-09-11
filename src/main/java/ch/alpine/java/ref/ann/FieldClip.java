// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldClip {
  /** @return permitted lower bound */
  String min();

  /** @return permitted upper bound */
  String max();
}
