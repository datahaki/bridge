// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.FieldOuterParam.NestedParam;
import ch.alpine.tensor.Scalar;

class RandomFieldsAssignmentTest {
  @Test
  void test() {
    Set<Scalar> set = new HashSet<>();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldOuterProduct = RandomFieldsAssignment.of(fieldOuterParam, () -> {
      set.add(fieldOuterParam.ratio);
    });
    assertEquals(set.size(), 0);
    fieldOuterProduct.randomize(3);
    assertEquals(set.size(), 3);
    fieldOuterProduct.randomize(7);
    assertEquals(set.size(), 10);
    fieldOuterProduct.randomize(100);
    assertEquals(set.size(), 10 + 100);
  }

  @Test
  void testgrid() {
    AtomicInteger atomicInteger = new AtomicInteger();
    NestedParam nestedParam = new NestedParam();
    FieldsAssignment fieldOuterProduct = RandomFieldsAssignment.of(nestedParam, () -> {
      atomicInteger.getAndIncrement();
    });
    assertEquals(atomicInteger.get(), 0);
    fieldOuterProduct.randomize(2);
    assertEquals(atomicInteger.get(), 2);
    fieldOuterProduct.randomize(10);
    assertEquals(atomicInteger.get(), 2 + 4);
  }
}
