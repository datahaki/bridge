// code by jph
package ch.alpine.curios.ref;

import java.awt.Window;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

class DialogFieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, new GuiExtension(), "here");
    WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
    System.out.println("non blocking");
    return dialogFieldsEditor;
  }

  static void main() {
    new DialogFieldsEditorDemo().run();
  }
}
