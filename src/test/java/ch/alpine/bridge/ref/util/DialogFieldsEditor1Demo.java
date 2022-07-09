// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.swing.LookAndFeels;

class DialogFieldsEditor1Demo {
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor.block(null, "here", new GuiExtension());
    // DialogFieldsEditor.block(null, "here", FieldsEditorParam.GLOBAL);
  }
}
