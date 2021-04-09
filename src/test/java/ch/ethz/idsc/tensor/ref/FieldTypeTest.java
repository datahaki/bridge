// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.mat.Pivots;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FieldTypeTest extends TestCase {
  public void testSimple() throws NoSuchFieldException, SecurityException {
    ParamContainerEnum paramContainerEnum = new ParamContainerEnum();
    Field field = paramContainerEnum.getClass().getField("pivots");
    assertFalse(FieldType.ENUM.isValidString(field, "some"));
    assertTrue(FieldType.ENUM.isValidString(field, "FIRST_NON_ZERO"));
  }

  public void testEnumToString() {
    String string = FieldType.toString(Pivots.ARGMAX_ABS);
    assertEquals(string, "ARGMAX_ABS");
  }

  public void testScalarToString() {
    Scalar scalar = Quantity.of(3, "m");
    String string = FieldType.toString(scalar);
    assertEquals(string, "3[m]");
  }
}
