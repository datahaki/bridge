// code by jph
package ch.alpine.java.ref.ann;

import ch.alpine.java.ref.gui.FieldsPanel;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import junit.framework.TestCase;

public class FieldLabelsTest extends TestCase {
  @FieldLabel(text = "nested %a")
  public final Nested[] nested1 = { new Nested(), new Nested() };
  @FieldLabel(text = "nested %d")
  public final Nested[] nested2 = { new Nested(), new Nested() };

  public static class Nested {
    public Scalar value = RealScalar.ONE;
  }

  public void testFormatFail() {
    FieldsPanel fieldsEditor = new FieldsPanel(this);
    assertEquals(fieldsEditor.list().size(), 4);
    fieldsEditor.getJScrollPane();
  }
}
