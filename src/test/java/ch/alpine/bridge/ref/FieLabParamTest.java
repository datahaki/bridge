// code by jph
package ch.alpine.bridge.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class FieLabParamTest {
  @Test
  public void testSimple() {
    ObjectProperties.string(new FieLabParam(4));
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new FieLabParam(4));
    fieldsPanel.createJScrollPane();
  }
}