// code by jph
package ch.alpine.java.ref.ann;

import ch.alpine.java.ref.PanelFieldsEditor;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import junit.framework.TestCase;

@ReflectionMarker
public class FieldLabelsTest extends TestCase {
  @FieldLabel(text = "nested %a")
  public final Nested[] nested1 = { new Nested(), new Nested() };
  @FieldLabel(text = "nested %d")
  public final Nested[] nested2 = { new Nested(), new Nested() };

  public static class Nested {
    public Scalar value = RealScalar.ONE;
  }

  public void testFormatFail() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(this);
    assertEquals(fieldsPanel.list().size(), 4);
    fieldsPanel.createJScrollPane();
  }
}
