// code by GRZ Technologies SA, jph
package ch.alpine.java.ref.ann;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** this marker annotation is a hint for the test framework, and developers
 * that a class is subject to introspection and reflection. */
@Retention(RetentionPolicy.SOURCE)
@Target(TYPE)
public @interface ReflectionMarker {
  // ---
}
