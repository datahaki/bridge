// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.wdog.Watchdog;

class ClassVisitorTest {
  @Test
  void testSimple() {
    Set<Class<?>> set = new HashSet<>();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void accept(String jarfile, Class<?> cls) {
        if (Watchdog.class.isAssignableFrom(cls))
          set.add(cls);
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(1 < set.size());
  }
}
