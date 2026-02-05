// code by jph
package showcase.demo;

import java.awt.Window;

import ch.alpine.bridge.lang.WindowProvider;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import showcase.data.MyConfig;

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
