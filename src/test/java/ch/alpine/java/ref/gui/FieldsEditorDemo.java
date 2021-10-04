// code by jph
package ch.alpine.java.ref.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.awt.LookAndFeels;
import ch.alpine.java.ref.SimpleParam;

public enum FieldsEditorDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.DARK.updateUI();
    SimpleParam simpleParam = new SimpleParam();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    FieldsPanel fieldsEditor = new FieldsPanel(simpleParam);
    fieldsEditor.addUniversalListener(() -> simpleParam.lookAndFeels.updateUI());
    JComponent jScrollPane = TestHelper.fieldsAndTextArea(fieldsEditor, simpleParam);
    jFrame.setContentPane(jScrollPane);
    jFrame.setBounds(100, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
