// code by jph
package ch.alpine.bridge.wdog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class StaticHelperTest {
  @Test
  public void testSimpleS1() {
    long nanos = StaticHelper.nanos(Quantity.of(3, "s"));
    assertEquals(nanos, 3000_000_000L);
  }

  @Test
  public void testSimpleS2() {
    long nanos = StaticHelper.nanos(Quantity.of(0.1, "s"));
    assertEquals(nanos, 100_000_000L);
  }

  @Test
  public void testSimpleMS1() {
    long nanos = StaticHelper.nanos(Quantity.of(88, "ms"));
    assertEquals(nanos, 88_000_000L);
  }

  @Test
  public void testSimpleMS2() {
    long nanos = StaticHelper.nanos(Quantity.of(0.88, "ms"));
    assertEquals(nanos, 880_000L);
  }

  @Test
  public void testSimpleUS1() {
    long nanos = StaticHelper.nanos(Quantity.of(17, "us"));
    assertEquals(nanos, 17_000L);
  }

  @Test
  public void testSimpleNS1() {
    long nanos = StaticHelper.nanos(Quantity.of(173, "ns"));
    assertEquals(nanos, 173);
  }
}
