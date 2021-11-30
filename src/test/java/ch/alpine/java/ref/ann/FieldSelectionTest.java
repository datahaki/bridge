// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Annotation;

import junit.framework.TestCase;

public class FieldSelectionTest extends TestCase {
  public void testSimple() {
    FieldSelection fieldSelection = new FieldSelection() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldSelection.class;
      }

      @Override
      public String[] array() {
        return new String[] { "fox", "over", "fence" };
      }
    };
    fieldSelection.array();
  }
}
