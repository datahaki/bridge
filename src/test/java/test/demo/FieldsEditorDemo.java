// code by jph
package test.demo;

import java.awt.Window;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.bridge.util.WindowSupplier;
import test.data.MyConfig;

class FieldsEditorDemo implements WindowSupplier {
  @Override
  public Window createWindow() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    return DialogFieldsEditor.show(null, new MyConfig(), "here");
  }

  static void main() {
    new FieldsEditorDemo().run();
  }
}
