// code by jph
package ch.alpine.java.lang;

import junit.framework.TestCase;

public class ClassDiscoveryTest extends TestCase {
  int count = 0;

  public void testSimple() {
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void classFound(String jarfile, Class<?> cls) {
        ++count;
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(1000 < count);
  }
}
