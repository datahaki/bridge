// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.GuiExtension;

class DialogFieldsEditorTest {
  @Test
  void test() {
    DialogFieldsEditor dialogFieldsEditor = new DialogFieldsEditor(null, "here", new GuiExtension());
    dialogFieldsEditor.fieldsEditor().addUniversalListener(() -> System.out.println("changed"));
  }

  @Test
  void testShow() {
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, "here", new GuiExtension());
    dialogFieldsEditor.getSelection();
  }
}
