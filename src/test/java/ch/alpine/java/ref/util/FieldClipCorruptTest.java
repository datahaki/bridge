// code by jph
package ch.alpine.java.ref.util;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.java.ref.gui.PanelFieldsEditor;
import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

@ReflectionMarker
public class FieldClipCorruptTest extends TestCase {
  @FieldClip(min = "0[A]", max = "3[W]")
  public Scalar current = Quantity.of(4, "A");

  public void testSimple() {
    FieldClipCorruptTest fieldClipCorrupt = new FieldClipCorruptTest();
    AssertFail.of(() -> new PanelFieldsEditor(fieldClipCorrupt));
  }
}
