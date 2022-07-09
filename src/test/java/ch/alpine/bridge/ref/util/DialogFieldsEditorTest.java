// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.GuiExtension;

class DialogFieldsEditorTest {
  @Test
  void test() {
    DialogFieldsEditor objectFieldsDialog = new DialogFieldsEditor(null, "here", new GuiExtension());
    objectFieldsDialog.fieldsEditor().addUniversalListener(() -> System.out.println("changed"));
  }
}
