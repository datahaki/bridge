// code by jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.bridge.ref.FieldWrap;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSelectionArray {
  /** Works on string, tensor, scalar, ...
   * 
   * Example return values:
   * {"1[%]", "2[%]", "3[%]"}
   * 
   * @return array of string expressions that can be converted
   * to objects via {@link FieldWrap#toValue(String)} */
  String[] value();
}
