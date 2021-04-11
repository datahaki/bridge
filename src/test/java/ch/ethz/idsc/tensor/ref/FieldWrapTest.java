// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.Pivots;
import ch.ethz.idsc.tensor.num.Pi;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FieldWrapTest extends TestCase {
  public void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainerEnum.class.getField("pivots");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertNull(fieldWrap.toValue("some"));
    Object object = fieldWrap.toValue("FIRST_NON_ZERO");
    assertEquals(object, Pivots.FIRST_NON_ZERO);
  }

  public void testEnumToString() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainerEnum.class.getField("pivots");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    String string = fieldWrap.toString(Pivots.ARGMAX_ABS);
    assertEquals(string, "ARGMAX_ABS");
  }

  public void testFieldTensor() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("shape");
    FieldWrap fieldType = FieldWraps.INSTANCE.wrap(field);
    assertTrue(fieldType.isValidValue(Tensors.vector(1, 2, 3)));
    assertFalse(fieldType.isValidValue(Tensors.fromString("{1, 2a, 3}")));
  }

  //
  public void testFieldScalar() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("abc");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertTrue(fieldWrap.isValidValue(Pi.VALUE));
  }

  public void testAnnotationInteger() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("integer");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertFalse(fieldWrap.isValidValue(Pi.VALUE));
    assertTrue(fieldWrap.isValidValue(RealScalar.of(123)));
  }

  public void testAnnotationClip() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertFalse(fieldWrap.isValidValue(Pi.VALUE));
    assertFalse(fieldWrap.isValidValue(RealScalar.of(123)));
    assertTrue(fieldWrap.isValidValue(Quantity.of(3000, "W")));
    assertFalse(fieldWrap.isValidValue(Quantity.of(1, "W")));
  }

  public void testEnum() {
    Object object = Pivots.ARGMAX_ABS;
    String string = ((Enum<?>) object).name();
    assertEquals(string, "ARGMAX_ABS");
  }
}
