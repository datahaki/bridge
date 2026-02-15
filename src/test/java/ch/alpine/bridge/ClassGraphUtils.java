// code by jph
package ch.alpine.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

/** VERSION 1.1 */
public record ClassGraphUtils<T>(Class<T> cls) {
  public List<T> getInstances(String... packageNames) {
    List<T> collection = new LinkedList<>();
    for (Class<?> implementation : getImplementations("ch")) {
      List<T> list = (List<T>) getInstances(implementation);
      collection.addAll(list);
    }
    return collection;
  }

  private List<Class<?>> getImplementations(String... packageNames) {
    try (ScanResult scanResult = new ClassGraph().enableAllInfo() //
        .acceptPackages(packageNames) //
        .scan()) {
      return scanResult.getClassesImplementing(cls.getName()) //
          .loadClasses();
    }
  }

  private List<T> getInstances(Class<?> implementation) {
    List<T> list = new LinkedList<>();
    for (Field field : implementation.getDeclaredFields())
      if (Modifier.isStatic(field.getModifiers()))
        try {
          field.setAccessible(true); // mandatory
          Object object = field.get(null);
          if (cls.isInstance(object)) {
            // IO.println("---");
            // IO.println(implementation);
            // IO.println(field);
            list.add(cls.cast(object));
          }
        } catch (Exception e) {
          System.err.println("error " + e.getMessage());
        }
    // ---
    if (implementation.isEnum()) {
      // enum constants are handled as fields above
    } else //
    if (implementation.isInterface()) {
      // ---
    } else //
    if (implementation.isRecord()) {
      // ---
    } else //
    if (implementation.isAnonymousClass()) {
      // ---
    } else //
      try {
        Constructor<?> constructor = implementation.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object object = constructor.newInstance();
        list.add(cls.cast(object));
      } catch (Exception exception) {
        // ---
      }
    return list;
  }
}
