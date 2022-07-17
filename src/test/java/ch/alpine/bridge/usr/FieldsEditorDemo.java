// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.ref.ex.MyConfig;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

public enum FieldsEditorDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    DialogFieldsEditor.show(null, "here", new MyConfig());
  }
}
