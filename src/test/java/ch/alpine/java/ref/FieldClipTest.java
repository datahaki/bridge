// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import junit.framework.TestCase;

public class FieldClipTest extends TestCase {
  public void testSimple() throws Exception {
    Field field = AnnotatedContainer.class.getField("clipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = TensorReflection.clip(fieldClip);
    assertEquals(clip.min(), RealScalar.of(2));
    assertEquals(clip.max(), RealScalar.of(6));
  }

  public void testQuantity() throws Exception {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = TensorReflection.clip(fieldClip);
    assertEquals(clip.min(), Quantity.of(2000, "kg*m^2*s^-3"));
    assertEquals(clip.max(), Quantity.of(6000, "kg*m^2*s^-3"));
  }
}
