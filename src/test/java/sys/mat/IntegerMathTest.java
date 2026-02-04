// code by jph
package sys.mat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.d.DiscreteUniformDistribution;

class IntegerMathTest {
  /** integer division with intuitive handling of negative numbers
   * 
   * for instance:
   * -5/ 7 == 0, but
   * floorDiv(-5, 7) == -1
   * 
   * @param a
   * @param b is positive
   * @return */
  private static int floorDiv(int a, int b) {
    assert 0 < b;
    return 0 <= a ? a / b : (a - b + 1) / b;
  }

  /** mod that behaves like in Matlab. for instance mod(-10, 3) == 2
   * 
   * @param index
   * @param size
   * @return matlab.mod(index, size) */
  private static int floorMod(int index, int size) {
    int value = index % size;
    // if value is below 0, then -size < value && value < 0.
    // For instance: -3%3==0, and -2%3==-2.
    return value < 0 ? size + value : value;
  }

  @Test
  void testFloorDiv() {
    for (int count = -40; count < 40; ++count)
      assertEquals(Math.floorDiv(count, 7), floorDiv(count, 7));
  }

  @Test
  void testFloorMod() {
    for (int count = -40; count < 40; ++count)
      assertEquals(Math.floorMod(count, 7), floorMod(count, 7));
  }

  /** integer division equivalent to double division followed by ceil
   * 
   * for instance:
   * 5/ 7 == 0, but
   * ceilDiv( 5, 7) == 1, since ceil(5./ 7.) == 1
   * 
   * @param a
   * @param b is positive
   * @return */
  public static int ceilDiv(int a, int b) {
    return Math.floorDiv(a + b - 1, b);
  }

  @RepeatedTest(50)
  void testCeilDiv() {
    Scalar sa = RandomVariate.of(DiscreteUniformDistribution.of(-100, 100));
    Scalar sb = RandomVariate.of(DiscreteUniformDistribution.of(1, 10));
    int a = Scalars.intValueExact(sa);
    int b = Scalars.intValueExact(sb);
    assertEquals(IntegerMath.ceilDiv(a, b), IntegerMath.ceilDiv(a, b));
  }
}
