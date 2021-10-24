// code by jph
package ch.alpine.java.ref.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.SimpleParam;

public enum PanelFieldsEditorDemo {
  ;
  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.lookAndFeels.updateUI();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(simpleParam);
    // panelFieldsEditor.addUniversalListener(() -> simpleParam.lookAndFeels.updateUI());
    TestHelper testHelper = new TestHelper(panelFieldsEditor, simpleParam);
    jFrame.setContentPane(testHelper.jPanel);
    jFrame.setBounds(100, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
