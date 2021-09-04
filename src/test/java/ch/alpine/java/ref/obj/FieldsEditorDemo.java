// code by jph
package ch.alpine.java.ref.obj;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.gui.FieldsEditor;

public enum FieldsEditorDemo {
  ;
  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JComponent jScrollPane = new FieldsEditor(simpleParam).getFieldsAndTextarea();
    jFrame.setContentPane(jScrollPane);
    jFrame.setBounds(100, 100, 500, 500);
    jFrame.setVisible(true);
  }
}
