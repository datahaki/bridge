// code by jph
package demo;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

enum DialogFieldsEditorDemo {
  ;
  static void main() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, new GuiExtension(), "here");
    WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
    System.out.println("non blocking");
  }
}
