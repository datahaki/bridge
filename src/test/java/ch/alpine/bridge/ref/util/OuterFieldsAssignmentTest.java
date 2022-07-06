// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.GuiExtension;

class OuterFieldsAssignmentTest {
  @Test
  void test() {
    Set<String> set = new HashSet<>();
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    OuterFieldsAssignment fieldOuterProduct = new OuterFieldsAssignment(fieldOuterParam, () -> {
      atomicInteger.getAndIncrement();
      assertEquals(fieldOuterParam.nestedParam[0].text, "abc");
      assertEquals(fieldOuterParam.nestedParam[1].text, "abc");
      set.add(ObjectProperties.join(fieldOuterParam));
    });
    fieldOuterProduct.forEach();
    assertEquals(atomicInteger.get(), 96);
    assertEquals(set.size(), 96);
  }

  @Test
  void testLimit() {
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    OuterFieldsAssignment fieldOuterProduct = new OuterFieldsAssignment(fieldOuterParam, () -> {
      atomicInteger.getAndIncrement();
      assertEquals(fieldOuterParam.nestedParam[0].text, "abc");
      assertEquals(fieldOuterParam.nestedParam[1].text, "abc");
    });
    fieldOuterProduct.randomize(15);
    assertEquals(atomicInteger.get(), 15);
  }

  @Test
  void testGuiExtensions() {
    AtomicInteger atomicInteger = new AtomicInteger();
    GuiExtension guiExtension = new GuiExtension();
    OuterFieldsAssignment fieldOuterProduct = new OuterFieldsAssignment(guiExtension, () -> {
      atomicInteger.getAndIncrement();
    });
    fieldOuterProduct.randomize(133);
    assertEquals(atomicInteger.get(), 133);
    fieldOuterProduct.restore();
  }
}
