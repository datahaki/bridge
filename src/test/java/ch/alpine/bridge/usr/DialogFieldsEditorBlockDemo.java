// code by jph
package ch.alpine.bridge.usr;

import java.util.Optional;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

enum DialogFieldsEditorBlockDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    Optional<GuiExtension> optional = DialogFieldsEditor.block(null, new GuiExtension(), "here");
    // WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
    System.out.println("blocking " + optional);
  }
}
