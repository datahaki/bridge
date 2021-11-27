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
 * <p>Even sub-parameter classes, that are never used stand-alone, should be
 * annotated to indicate to the programmer, that a change of field name may
 * influence the serialization and de-serialization of instances.
 * 
 * @see ObjectFields
 * @see ClassFieldCheck */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ReflectionMarker {
  // ---
}
