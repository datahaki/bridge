// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testSimpleS1() {
    long nanos = StaticHelper.nanos(Quantity.of(3, "s"));
    assertEquals(nanos, 3000_000_000L);
  }

  public void testSimpleS2() {
    long nanos = StaticHelper.nanos(Quantity.of(0.1, "s"));
    assertEquals(nanos, 100_000_000L);
  }

  public void testSimpleMS1() {
    long nanos = StaticHelper.nanos(Quantity.of(88, "ms"));
    assertEquals(nanos, 88_000_000L);
  }

  public void testSimpleMS2() {
    long nanos = StaticHelper.nanos(Quantity.of(0.88, "ms"));
    assertEquals(nanos, 880_000L);
  }

  public void testSimpleUS1() {
    long nanos = StaticHelper.nanos(Quantity.of(17, "us"));
    assertEquals(nanos, 17_000L);
  }

  public void testSimpleNS1() {
    long nanos = StaticHelper.nanos(Quantity.of(173, "ns"));
    assertEquals(nanos, 173);
  }
}
