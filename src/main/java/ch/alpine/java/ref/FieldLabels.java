// code by gjoel, jph
package ch.alpine.java.ref;

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
public @interface FieldLabels {
  /** @return text of labels */
  String[] text();
}
