// code by jph
package ch.alpine.bridge.ref.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.lang.ClassVisitor;
import ch.alpine.bridge.ref.ObjectFields;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

public class ClassFieldCheck implements ClassVisitor {
  private final InvalidFieldCollection invalidFieldCollection = new InvalidFieldCollection();
  private final InvalidAnnotationCollection invalidAnnotationCollection = new InvalidAnnotationCollection();
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
        try {
          ObjectFields.of(object, invalidAnnotationCollection);
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
    List<FieldValueContainer> list = new ArrayList<>();
    list.addAll(invalidFieldCollection.list());
    list.addAll(invalidAnnotationCollection.list());
    return list;
  }
}
