// code by jph
package ch.alpine.bridge.ref.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.lang.ClassVisitor;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

/** class visitor that visits classes that are annotated with
 * {@link ReflectionMarker} */
public class ClassFieldCheck implements ClassVisitor {
  private final InvalidFieldDetection invalidFieldDetection = new InvalidFieldDetection();
  private final List<Class<?>> inspected = new LinkedList<>();
  private final List<Class<?>> failures = new LinkedList<>();

  @Override // from ClassVisitor
  public void accept(String jarfile, Class<?> cls) {
    ReflectionMarker reflectionMarker = cls.getAnnotation(ReflectionMarker.class);
    if (Objects.nonNull(reflectionMarker)) {
      Object object = null;
      try {
        object = cls.getDeclaredConstructor().newInstance();
      } catch (Exception exception) {
        // ---
      }
      if (Objects.nonNull(object)) {
        inspected.add(cls);
        try {
          ObjectFields.of(object, invalidFieldDetection);
        } catch (Exception exception) {
          failures.add(cls);
        }
      }
    }
  }

  /** @return collection of all inspected classes */
  public List<Class<?>> getInspected() {
    return inspected;
  }

  /** @return collection of all classes where a failure occurred */
  public List<Class<?>> getFailures() {
    return failures;
  }

  /** @return invalid fields */
  public List<FieldValueContainer> invalidFields() {
    List<FieldValueContainer> list = new ArrayList<>();
    list.addAll(invalidFieldDetection.list());
    return list;
  }
}
