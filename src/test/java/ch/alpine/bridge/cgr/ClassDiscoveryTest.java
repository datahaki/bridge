// code by jph
package ch.alpine.bridge.cgr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.Int;

class ClassDiscoveryTest {
  @Test
  void testSimple() throws Exception {
    Int myI = new Int();
    ClassVisitor classVisitor = new ClassVisitor() {
      @Override
      public void accept(String jarfile, Class<?> cls) {
        myI.getAndIncrement();
      }
    };
    ClassDiscovery.execute(ClassPaths.getDefault(), classVisitor);
    assertTrue(2000 < myI.intValue());
  }
}
