// code by jph
package ch.alpine.java.lang;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class ClassDiscoveryTest extends TestCase {
  public void testSimple() {
    AtomicInteger count = new AtomicInteger();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void accept(String jarfile, Class<?> cls) {
        count.getAndIncrement();
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(1000 < count.intValue());
  }
}
