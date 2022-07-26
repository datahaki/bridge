// code by jph
package ch.alpine.bridge.usr;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ex.EmptyParam;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public enum EmptyDemo {
  ;
  public static void main(String[] args) {
    EmptyParam emptyParam = new EmptyParam();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(emptyParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
    jFrame.setBounds(500, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
