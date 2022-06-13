// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class FieldLabelArrayTest {
  @FieldLabelArray("asd")
  public Integer integer = 3;

  @Test
  void testSimple() throws NoSuchFieldException, SecurityException {
    Field field = FieldLabelArrayTest.class.getField("integer");
    FieldLabelArray fieldLabelArray = field.getAnnotation(FieldLabelArray.class);
    assertEquals(fieldLabelArray.value().length, 1);
  }
}
