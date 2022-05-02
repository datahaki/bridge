// code by gjoel, jph
package ch.alpine.bridge.ref.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
/** Hint: if the array specified by the text function has insufficient length,
 * then the default field label is used in the GUI. */
public @interface FieldLabelArray {
  /** @return array of text of labels */
  String[] value();
}
