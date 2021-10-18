// code by jph
package ch.alpine.java.ref.ann;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.util.ClassFieldCheck;

/** This marker annotation is a hint for the test framework, and developers that
 * a class is subject to introspection and reflection.
 * 
 * <p>Sub-parameter classes, which are never used stand-alone, do not have to be
 * annotated.
 * 
 * @see ObjectFields
 * @see ClassFieldCheck */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ReflectionMarker {
  // ---
}
