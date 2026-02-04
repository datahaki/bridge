// code by jph
package demo;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

enum FieldsEditorDemo {
  ;
  static void main() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor.show(null, new MyConfig(), "here");
  }
}
