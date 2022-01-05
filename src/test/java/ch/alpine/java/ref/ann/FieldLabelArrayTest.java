// code by jph
package ch.alpine.java.ref.ann;

import java.lang.reflect.Field;

import junit.framework.TestCase;

public class FieldLabelArrayTest extends TestCase {
  @FieldLabelArray("asd")
  public Integer integer = 3;

  public void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = FieldLabelArrayTest.class.getField("integer");
    FieldLabelArray fieldLabelArray = field.getAnnotation(FieldLabelArray.class);
    assertEquals(fieldLabelArray.value().length, 1);
  }
}
