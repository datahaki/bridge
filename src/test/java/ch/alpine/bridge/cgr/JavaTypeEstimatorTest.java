// code by jph
package ch.alpine.bridge.cgr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.cgr.JavaTypeEstimator.DeclaredType;

class JavaTypeEstimatorTest {
  @Test
  void test() {
    String code = "class JavaTypeEstimatorTest {";
    DeclaredType declaredType = JavaTypeEstimator.estimateType(code);
    assertEquals(declaredType, DeclaredType.CLASS);
  }
}
