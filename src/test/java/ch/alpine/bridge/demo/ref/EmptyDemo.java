// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.Window;

import javax.swing.JFrame;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.EmptyParam;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

enum EmptyDemo implements WindowProvider {
  INSTANCE;

  @Override
  public Window getWindow() {
    EmptyParam emptyParam = new EmptyParam();
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(emptyParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
    return jFrame;
  }

  static void main() {
    INSTANCE.runStandalone();
  }
}
