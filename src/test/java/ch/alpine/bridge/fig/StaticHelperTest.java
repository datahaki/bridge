// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
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
}
