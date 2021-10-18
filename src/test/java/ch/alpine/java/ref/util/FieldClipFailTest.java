// code by jph
package ch.alpine.java.ref.util;

import ch.alpine.java.ref.gui.PanelFieldsEditor;
import ch.alpine.java.util.AssertFail;
import junit.framework.TestCase;

public class FieldClipFailTest extends TestCase {
  public void testSimple() {
    FieldClipCorruptExample fieldClipCorruptExample = new FieldClipCorruptExample();
    AssertFail.of(() -> new PanelFieldsEditor(fieldClipCorruptExample));
  }
}
