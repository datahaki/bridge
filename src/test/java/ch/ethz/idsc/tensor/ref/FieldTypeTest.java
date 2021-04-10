// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.Pivots;
import ch.ethz.idsc.tensor.num.Pi;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FieldTypeTest extends TestCase {
  public void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainerEnum.class.getField("pivots");
    assertFalse(FieldType.ENUM.isValidString(field, "some"));
    assertTrue(FieldType.ENUM.isValidString(field, "FIRST_NON_ZERO"));
  }

  public void testEnumToString() {
    String string = FieldType.ENUM.toString(Pivots.ARGMAX_ABS);
    assertEquals(string, "ARGMAX_ABS");
  }

  public void testScalarToString() {
    Scalar scalar = Quantity.of(3, "m");
    String string = FieldType.SCALAR.toString(scalar);
    assertEquals(string, "3[m]");
  }

  public void testFieldTensor() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("shape");
    assertTrue(FieldType.TENSOR.isValidValue(field, Tensors.vector(1, 2, 3)));
    assertFalse(FieldType.TENSOR.isValidValue(field, Tensors.fromString("{1, 2a, 3}")));
  }

  public void testFieldScalar() throws NoSuchFieldException, SecurityException {
    Field field = ParamContainer.class.getField("abc");
    assertTrue(FieldType.SCALAR.isValidValue(field, Pi.VALUE));
    assertFalse(FieldType.SCALAR.isValidValue(field, "asd"));
    assertFalse(FieldType.SCALAR.isValidValue(field, Scalars.fromString("asd")));
  }

  public void testAnnotationInteger() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("integer");
    assertFalse(FieldType.SCALAR.isValidValue(field, Pi.VALUE));
    assertTrue(FieldType.SCALAR.isValidValue(field, RealScalar.of(123)));
  }

  public void testAnnotationClip() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    assertFalse(FieldType.SCALAR.isValidValue(field, Pi.VALUE));
    assertFalse(FieldType.SCALAR.isValidValue(field, RealScalar.of(123)));
    assertTrue(FieldType.SCALAR.isValidValue(field, Quantity.of(3000, "W")));
    assertFalse(FieldType.SCALAR.isValidValue(field, Quantity.of(1, "W")));
  }

  public void testFieldColor() {
    String string = FieldType.COLOR.toString(Color.CYAN);
    assertEquals(string, "{0, 255, 255, 255}");
  }
}
