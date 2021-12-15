// code by jph
package ch.alpine.java.ref.ann;

import java.lang.annotation.Annotation;

import junit.framework.TestCase;

public class FieldSelectionTest extends TestCase {
  public void testSimple() {
    FieldSelectionArray fieldSelection = new FieldSelectionArray() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldSelectionArray.class;
      }

      @Override
      public String[] values() {
        return new String[] { "fox", "over", "fence" };
      }
    };
    fieldSelection.values();
  }
}
