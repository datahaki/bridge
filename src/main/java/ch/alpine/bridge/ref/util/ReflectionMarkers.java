// code by jph
package ch.alpine.bridge.ref.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.alpine.bridge.cgr.ClassHierarchy;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

public enum ReflectionMarkers {
  INSTANCE;

  private final Set<Class<?>> checked = new HashSet<>();
  private final Set<Class<?>> missing = new HashSet<>();
  private final ObjectFieldVisitor objectFieldVisitor = new ObjectFieldAll() {
    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      expected(fieldWrap.getField().getDeclaringClass());
    }
  };
  private boolean DEBUG_PRINT = true;

  ReflectionMarkers() {
    checked.add(Object.class);
    checked.add(Enum.class);
    checked.add(Record.class);
  }

  public void disableDebugPrint() {
    DEBUG_PRINT = false;
  }

  /** function checks if class of given object has been analyzed
   * for the presence of {@link ReflectionMarker} annotation.
   * the annotation is also expected on all nested classes, and
   * super types.
   * 
   * @param object non-null */
  public synchronized void register(Object object) {
    Class<?> cls = object.getClass();
    if (!checked.contains(cls)) {
      ClassHierarchy.of(cls).forEach(this::expected);
      ObjectFields.of(object, objectFieldVisitor);
    }
  }

  private void expected(Class<?> cls) {
    if (checked.add(cls)) {
      ReflectionMarker reflectionMarker = cls.getAnnotation(ReflectionMarker.class);
      // careful: the if statement modifies the set `missing`
      if (Objects.isNull(reflectionMarker) && //
          missing.add(cls) && //
          DEBUG_PRINT)
        System.err.println("hint: use @ReflectionMarker on " + cls);
    }
  }

  /** @return set of all classes that where discovered up to this point
   * with missing {@link ReflectionMarker} annotation */
  public synchronized Set<Class<?>> missing() {
    return Set.copyOf(missing);
  }
}
