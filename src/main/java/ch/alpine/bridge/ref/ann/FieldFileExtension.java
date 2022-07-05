// code by gjoel
package ch.alpine.bridge.ref.ann;

import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** annotation for a field of type {@link File} */
@Documented
@Repeatable(FieldFileExtension.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldFileExtension {
  /** @return description of the intended files */
  String description();

  /** @return array containing all possible extensions of the intended files */
  String[] extensions();

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface List {
    FieldFileExtension[] value();
  }
}
