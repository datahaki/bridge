// code by jph
package ch.alpine.java.ref;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.util.PanelFieldsEditor;

public enum FieldsEditorDemo {
  ;
  public static void main(String[] args) {
    MyConfig myConfig = new MyConfig();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(myConfig);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("my config changed"));
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
    jFrame.setBounds(100, 100, 320, 200);
    jFrame.setVisible(true);
  }
}
