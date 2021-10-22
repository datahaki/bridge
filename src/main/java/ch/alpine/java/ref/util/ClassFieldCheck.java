// code by jph
package ch.alpine.java.ref.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.alpine.java.lang.ClassVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.ReflectionMarker;

public class ClassFieldCheck implements ClassVisitor {
  private final InvalidFieldCollection invalidFieldCollection = new InvalidFieldCollection();
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
          ObjectFields.of(object, invalidFieldCollection);
        } catch (Exception exception) {
          failures.add(cls);
        }
      }
    }
  }

  public List<Class<?>> getInspected() {
    return inspected;
  }

  public List<Class<?>> getFailures() {
    return failures;
  }

  public List<FieldValueContainer> invalidFields() {
    return invalidFieldCollection.list();
  }
}