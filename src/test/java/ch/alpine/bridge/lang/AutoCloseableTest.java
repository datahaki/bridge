// code by GRZ Technologies SA, jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class Inner implements AutoCloseable {
  static int call_close = 0;

  public Inner() {
    throw new RuntimeException();
  }

  @Override
  public void close() throws Exception {
    ++call_close;
    fail();
  }
}

/** this demo shows that if the constructor throws an exception
 * then the close function is not called. */
class AutoCloseableTest {
  @Test
  void test() {
    try (Inner inner = new Inner()) {
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(Inner.call_close, 0);
  }
}
