// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.GuiExtension;

class FieldOuterProductTest {
  @Test
  void test() {
    Set<String> set = new HashSet<>();
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterProduct.forEach(new FieldOuterParam(), e -> {
      atomicInteger.getAndIncrement();
      assertEquals(e.nestedParam[0].text, "abc");
      assertEquals(e.nestedParam[1].text, "abc");
      set.add(ObjectProperties.join(e));
    });
    assertEquals(atomicInteger.get(), 96);
    assertEquals(set.size(), 96);
  }

  @Test
  void testLimit() {
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterProduct.randomize(new FieldOuterParam(), e -> {
      atomicInteger.getAndIncrement();
      assertEquals(e.nestedParam[0].text, "abc");
      assertEquals(e.nestedParam[1].text, "abc");
    }, 15);
    assertEquals(atomicInteger.get(), 15);
  }

  @Test
  void testGuiExtensions() {
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterProduct.randomize(new GuiExtension(), e -> {
      atomicInteger.getAndIncrement();
    }, 133);
    assertEquals(atomicInteger.get(), 133);
  }
}
