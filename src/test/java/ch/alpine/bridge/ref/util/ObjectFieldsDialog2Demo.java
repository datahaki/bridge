// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.GuiExtension;

class ObjectFieldsDialog2Demo {
  public static void main(String[] args) {
    ObjectFieldsDialog objectFieldsDialog = new ObjectFieldsDialog(null, "here", new GuiExtension());
    objectFieldsDialog.fieldsEditor().addUniversalListener(() -> System.out.println("changed"));
    objectFieldsDialog.setVisible(true);
  }
}
