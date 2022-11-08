// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class StaticHelperTest {
  @Test
  void testSimple() {
    assertEquals(StaticHelper.class.getModifiers() & 1, 0);
  }

  @Test
  void testDateTime() {
    DateTime scalar = DateTime.now();
    Clip clip = Clips.interval(scalar, scalar);
    clip = StaticHelper.nonZero(clip);
    assertEquals(clip.width(), Quantity.of(2, "s"));
  }

  @Test
  void testQuantity() {
    Scalar scalar = Quantity.of(4, "m");
    Clip clip = Clips.interval(scalar, scalar);
    clip = StaticHelper.nonZero(clip);
    assertEquals(clip.width(), Quantity.of(2, "m"));
  }

  @Test
  void testRatio1() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Scalar r = StaticHelper.ratio(a, Tensors.vector(400, 400));
    assertEquals(r, Quantity.of(100, "m^-1"));
  }

  @Test
  void testRatio2() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Scalar r = StaticHelper.ratio(a, Tensors.vector(200, 400));
    assertEquals(r, Quantity.of(RationalScalar.of(200, 3), "m^-1"));
  }
}
