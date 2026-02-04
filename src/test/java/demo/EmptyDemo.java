// code by jph
package demo;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

enum EmptyDemo {
  ;
  static void main() {
    EmptyParam emptyParam = new EmptyParam();
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(emptyParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
    jFrame.setBounds(500, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
