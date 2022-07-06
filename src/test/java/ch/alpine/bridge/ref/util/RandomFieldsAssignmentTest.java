// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;

class RandomFieldsAssignmentTest {
  @Test
  void test() {
    Set<Scalar> set = new HashSet<>();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    RandomFieldsAssignment fieldOuterProduct = new RandomFieldsAssignment(fieldOuterParam, () -> {
      set.add(fieldOuterParam.ratio);
    });
    assertEquals(set.size(), 0);
    fieldOuterProduct.randomize(3);
    assertEquals(set.size(), 3);
    fieldOuterProduct.randomize(7);
    assertEquals(set.size(), 10);
  }
}
