// code by jph
package ch.alpine.java.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class FieldLabelsTest {
  @FieldLabel("nested %a")
  public final Nested[] nested1 = { new Nested(), new Nested() };
  @FieldLabel("nested %d")
  public final Nested[] nested2 = { new Nested(), new Nested() };

  public static class Nested {
    public Scalar value = RealScalar.ONE;
  }

  @Test
  public void testFormatFail() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(this);
    assertEquals(fieldsPanel.list().size(), 4);
    fieldsPanel.createJScrollPane();
  }
}
