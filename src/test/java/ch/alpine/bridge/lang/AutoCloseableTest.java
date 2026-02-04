// code by GRZ Technologies SA, jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/** this demo shows that if the constructor throws an exception
 * then the close function is not called. */
class AutoCloseableTest {
  static class Inner1 implements AutoCloseable {
    public Inner1() {
      throw new RuntimeException();
    }

    @Override
    public void close() {
      fail();
    }
  }

  @Test
  void test1() {
    try (Inner1 _ = new Inner1()) {
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  static class Inner2 implements AutoCloseable {
    static int count = 0;

    @Override
    public void close() {
      ++count;
    }
  }

  @Test
  void test2a() {
    try (Inner2 _ = new Inner2()) {
      throw new RuntimeException();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(Inner2.count, 1);
  }

  @Test
  void test2b() throws InterruptedException {
    try (Inner2 _ = new Inner2()) {
    } catch (Exception exception) {
      // ---
    }
    assertEquals(Inner2.count, 2);
  }
}
