// code by jph
package test.demo;

import java.awt.Window;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.bridge.util.WindowSupplier;
import test.data.GuiExtension;

class DialogFieldsEditorDemo implements WindowSupplier {
  @Override
  public Window createWindow() {
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
