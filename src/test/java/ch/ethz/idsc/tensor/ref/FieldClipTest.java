// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class FieldClipTest extends TestCase {
  public void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("clipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Optional<Clip> optional = TensorReflection.of(fieldClip);
    Clip clip = optional.get();
    assertEquals(clip.min(), RealScalar.of(2));
    assertEquals(clip.max(), RealScalar.of(6));
  }

  public void testQuantity() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Optional<Clip> optional = TensorReflection.of(fieldClip);
    Clip clip = optional.get();
    assertEquals(clip.min(), Quantity.of(2000, "kg*m^2*s^-3"));
    assertEquals(clip.max(), Quantity.of(6000, "kg*m^2*s^-3"));
  }
}
