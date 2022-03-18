// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Test;

public class FieldSelectionTest {
  @Test
  public void testSimple() {
    FieldSelectionArray fieldSelection = new FieldSelectionArray() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldSelectionArray.class;
      }

      @Override
      public String[] value() {
        return new String[] { "fox", "over", "fence" };
      }
    };
    fieldSelection.value();
  }
}
