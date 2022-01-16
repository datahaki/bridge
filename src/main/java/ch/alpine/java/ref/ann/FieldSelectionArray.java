// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Quote:
 * "If an annotation type has only one element with named "value",
 * you can omit the name from name=value pair from your annotation."
 * www.java2s.com/example/java-book/annotation-shorthand.html */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSelectionArray {
  /** Works on string, tensor, scalar, ...
   * 
   * Example return values:
   * {"1[%]", "2[%], "3[%]"}
   * 
   * @return */
  String[] value();
}
