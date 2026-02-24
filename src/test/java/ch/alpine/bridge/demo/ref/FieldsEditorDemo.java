// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.Window;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.MyConfig;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;

class FieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    return DialogFieldsEditor.show(null, new MyConfig(), "here");
  }

  static void main() {
    new FieldsEditorDemo().runStandalone();
  }
}
