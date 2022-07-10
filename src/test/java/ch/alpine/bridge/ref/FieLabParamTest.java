// code by jph
package ch.alpine.bridge.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.FieLabParam;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class FieLabParamTest {
  @Test
  void testSimple() {
    ObjectProperties.join(new FieLabParam(4));
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new FieLabParam(4));
    fieldsPanel.createJScrollPane();
  }
}
