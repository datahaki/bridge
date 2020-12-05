// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldSubdivide {
  /** @return string expression of tensor at start */
  String start();

  /** @return string expression of tensor at end */
  String end();

  /** @return positive number of intervals dividing the range start() to end() */
  int intervals();
}
