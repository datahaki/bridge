// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.swing.LookAndFeels;

class DialogFieldsEditor1Demo {
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, "here", new GuiExtension());
    WindowClosed.runs(dialogFieldsEditor, () -> {
      System.out.println(dialogFieldsEditor.getSelection());
    });
    // DialogFieldsEditor.block(null, "here", FieldsEditorParam.GLOBAL);
  }
}
