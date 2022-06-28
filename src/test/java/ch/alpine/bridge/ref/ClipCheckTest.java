// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.sca.Clips;

class ClipCheckTest {
  @Test
  void test() {
    ClipCheck clipCheck = new ClipCheck(Clips.unit());
    assertFalse(clipCheck.test(DoubleScalar.INDETERMINATE));
    assertFalse(clipCheck.test(DoubleScalar.POSITIVE_INFINITY));
    assertFalse(clipCheck.test(DoubleScalar.NEGATIVE_INFINITY));
  }

  @Test
  void testPackageVisibility() {
    assertFalse(Modifier.isPublic(ClipCheck.class.getModifiers()));
  }
}
