// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.ParamContainer;
import ch.alpine.bridge.ref.ex.ParamContainerEnum;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;

class FieldWrapTest {
  @Test
  void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainerEnum.class.getField("pivots");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertNull(fieldWrap.toValue("some"));
    Object object = fieldWrap.toValue("FIRST_NON_ZERO");
    assertEquals(object, Pivots.FIRST_NON_ZERO);
  }

  @Test
  void testEnumToString() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainerEnum.class.getField("pivots");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    String string = fieldWrap.toString(Pivots.ARGMAX_ABS);
    assertEquals(string, "ARGMAX_ABS");
  }

  @Test
  void testFieldTensor() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("shape");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertTrue(fieldWrap.isValidValue(Tensors.vector(1, 2, 3)));
    assertFalse(fieldWrap.isValidValue(Tensors.fromString("{1, 2a, 3}")));
  }

  //
  @Test
  void testFieldScalar() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("abc");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertTrue(fieldWrap.isValidValue(Pi.VALUE));
  }

  @Test
  void testAnnotationInteger() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("integer");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertFalse(fieldWrap.isValidValue(Pi.VALUE));
    assertTrue(fieldWrap.isValidValue(RealScalar.of(123)));
  }

  @Test
  void testAnnotationClip() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
    assertFalse(fieldWrap.isValidValue(Pi.VALUE));
    assertFalse(fieldWrap.isValidValue(RealScalar.of(123)));
    assertTrue(fieldWrap.isValidValue(Quantity.of(3000, "W")));
    assertFalse(fieldWrap.isValidValue(Quantity.of(1, "W")));
  }

  @Test
  void testEnum() {
    Object object = Pivots.ARGMAX_ABS;
    String string = ((Enum<?>) object).name();
    assertEquals(string, "ARGMAX_ABS");
  }
}
