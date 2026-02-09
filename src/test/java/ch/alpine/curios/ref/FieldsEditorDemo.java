// code by jph
package ch.alpine.curios.ref;

import java.awt.Window;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.MyConfig;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

class FieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    return DialogFieldsEditor.show(null, new MyConfig(), "here");
  }

  static void main() {
    new FieldsEditorDemo().run();
  }
}
