// code by jph
package ch.alpine.java.ref;

import ch.alpine.java.ref.gui.FieldsEditor;
import junit.framework.TestCase;

public class FieLabParamTest extends TestCase {
  public void testSimple() {
    ObjectProperties.string(new FieLabParam(4));
    FieldsEditor fieldsEditor = new FieldsEditor(new FieLabParam(4));
    fieldsEditor.getJScrollPane();
  }
}
