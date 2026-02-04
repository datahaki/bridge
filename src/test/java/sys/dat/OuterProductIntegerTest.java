package sys.dat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class OuterProductIntegerTest {
  @Test
  void test() {
    OuterProductInteger outerProductInteger = new OuterProductInteger(new int[] { 2, 3, 4 });
    assertEquals(outerProductInteger.next(), List.of(0, 0, 0));
    assertEquals(outerProductInteger.next(), List.of(1, 0, 0));
    assertEquals(outerProductInteger.next(), List.of(0, 1, 0));
    assertEquals(outerProductInteger.next(), List.of(1, 1, 0));
  }
}
