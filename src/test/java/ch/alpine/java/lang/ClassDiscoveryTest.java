// code by jph
package ch.alpine.java.lang;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.ReflectionMarker;
import junit.framework.TestCase;

public class ClassDiscoveryTest extends TestCase {
  public void testSimple() {
    AtomicInteger count = new AtomicInteger();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void classFound(String jarfile, Class<?> cls) {
        count.getAndIncrement();
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(1000 < count.intValue());
  }

  ObjectFieldVisitor asd = new ObjectFieldVisitor() {
    @Override
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      boolean isValidValue = fieldWrap.isValidValue(value);
      if (!isValidValue)
        System.err.println(key + " " + value + " ");
    }
  };

  public void testAnnotation() {
    AtomicInteger count = new AtomicInteger();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void classFound(String jarfile, Class<?> cls) {
        ReflectionMarker reflectionMarker = cls.getAnnotation(ReflectionMarker.class);
        if (Objects.nonNull(reflectionMarker)) {
          // System.out.println(cls.getSimpleName());
          count.getAndIncrement();
          try {
            Object object = cls.getDeclaredConstructor().newInstance();
            // System.out.println("worked");
            ObjectFields.of(object, asd);
          } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(3 < count.intValue());
  }
}
