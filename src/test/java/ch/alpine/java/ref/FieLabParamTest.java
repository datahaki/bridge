// code by jph
package ch.alpine.java.ref;

import junit.framework.TestCase;

public class FieLabParamTest extends TestCase {
  public void testSimple() {
    ObjectProperties.string(new FieLabParam(4));
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new FieLabParam(4));
    fieldsPanel.createJScrollPane();
  }
}
