// code by jph
package ch.alpine.java.ref;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import ch.alpine.javax.swing.LookAndFeels;

public enum PanelFieldsEditorDemo {
  ;
  public static void main(String[] args) throws Exception {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.lookAndFeels = LookAndFeels.LIGHT;
    simpleParam.lookAndFeels.updateUI();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(simpleParam);
    panelFieldsEditor.addUniversalListener(() -> {
      // TODO it's probably a good idea to only invoke the l&f once at the beginning
      try {
        // simpleParam.lookAndFeels.updateUI();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    TestHelper testHelper = new TestHelper(panelFieldsEditor, simpleParam);
    jFrame.setContentPane(testHelper.jPanel);
    jFrame.setBounds(100, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
