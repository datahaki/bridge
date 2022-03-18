// code by jph
package ch.alpine.java.ref.ann;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;

@ReflectionMarker
public class FieldClipTest {
  @FieldClip(min = "0[A]", max = "3[W]")
  public Scalar current = Quantity.of(4, "A");

  @Test
  public void testFailEx() {
    FieldClipTest fieldClipCorrupt = new FieldClipTest();
    AssertFail.of(() -> new PanelFieldsEditor(fieldClipCorrupt));
  }
}
