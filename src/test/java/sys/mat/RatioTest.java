package sys.mat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RatioTest {
  @Test
  void test() {
    Ratio ratio = new Ratio(2 * 7, 3 * 7);
    assertEquals(ratio.divBoth(7), new Ratio(2, 3));
    assertEquals(ratio.divBoth(7).hashCode(), new Ratio(2, 3).hashCode());
    int value = 1;
    value <<= 6;
    assertEquals(value, 64);
  }
}
