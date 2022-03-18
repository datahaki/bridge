// code by jph
package ch.alpine.java.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;

public class FieLabParamTest {
  @Test
  public void testSimple() {
    ObjectProperties.string(new FieLabParam(4));
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new FieLabParam(4));
    fieldsPanel.createJScrollPane();
  }
}
