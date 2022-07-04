// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class FieldOuterProductTest {
  @Test
  void test() {
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterProduct.forEach(new FieldOuterParam(), e -> {
      atomicInteger.getAndIncrement();
      assertEquals(e.nestedParam[0].text, "abc");
      assertEquals(e.nestedParam[1].text, "abc");
    });
    assertEquals(atomicInteger.get(), 96);
  }
}
