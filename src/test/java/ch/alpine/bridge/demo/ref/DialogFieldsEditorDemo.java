// code by jph
package ch.alpine.bridge.demo.ref;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;

class DialogFieldsEditorDemo {
  static void main() {
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, new GuiExtension(), "here");
    WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
  }
}
