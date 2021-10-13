// code by jph
package ch.alpine.java.lang;

import java.util.HashSet;
import java.util.Set;

import ch.alpine.java.wdog.Watchdog;
import junit.framework.TestCase;

public class ClassVisitorTest extends TestCase {
  public void testSimple() {
    Set<Class<?>> set = new HashSet<>();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void classFound(String jarfile, Class<?> cls) {
        if (Watchdog.class.isAssignableFrom(cls))
          set.add(cls);
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(1 < set.size());
  }
}
