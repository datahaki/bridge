// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

enum DialogFieldsEditorDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, "here", new GuiExtension());
    WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
  }
}
