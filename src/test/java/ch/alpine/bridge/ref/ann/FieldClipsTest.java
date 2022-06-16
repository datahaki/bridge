// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.AnnotatedContainer;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;

class FieldClipsTest {
  @Test
  void testSimple() throws Exception {
    Field field = AnnotatedContainer.class.getField("clipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.of(fieldClip);
    assertEquals(clip.min(), RealScalar.of(2));
    assertEquals(clip.max(), RealScalar.of(6));
  }

  @Test
  void testQuantity() throws Exception {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.of(fieldClip);
    assertEquals(clip.min(), Quantity.of(2000, "kg*m^2*s^-3"));
    assertEquals(clip.max(), Quantity.of(6000, "kg*m^2*s^-3"));
  }

  @Test
  void testIssue1() {
    Clip clip = FieldClips.of(Quantity.of(0, "L*min^-1"), Quantity.of(20, "L*min^-1"));
    assertTrue(clip.isInside(UnitSystem.SI().apply(Quantity.of(20, "L*min^-1"))));
    assertTrue(clip.isInside(UnitSystem.SI().apply(Quantity.of(20.0, "L*min^-1"))));
  }

  @Test
  void testIssue2() {
    Clip clip = FieldClips.of(Quantity.of(0, "L*min^-1"), Quantity.of(20.0, "L*min^-1"));
    assertTrue(clip.isInside(UnitSystem.SI().apply(Quantity.of(20, "L*min^-1"))));
    assertTrue(clip.isInside(UnitSystem.SI().apply(Quantity.of(20.0, "L*min^-1"))));
  }

  @FieldClip(min = "0[super]", max = "Infinity[super]")
  public Scalar awesome = Quantity.of(4, "super");

  @Test
  void testSimple2() throws NoSuchFieldException, SecurityException {
    FieldClipsTest fieldClipsTest = new FieldClipsTest();
    Field field = fieldClipsTest.getClass().getField("awesome");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.of(fieldClip);
    assertEquals(clip.max(), Quantity.of(DoubleScalar.POSITIVE_INFINITY, "super"));
  }
}
