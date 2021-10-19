// code by jph
package ch.alpine.java.ref.ann;

import ch.alpine.java.ref.gui.PanelFieldsEditor;
import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

@ReflectionMarker
public class FieldClipTest extends TestCase {
  @FieldClip(min = "0[A]", max = "3[W]")
  public Scalar current = Quantity.of(4, "A");

  public void testSimple() {
    FieldClipTest fieldClipCorrupt = new FieldClipTest();
    AssertFail.of(() -> new PanelFieldsEditor(fieldClipCorrupt));
  }
}
