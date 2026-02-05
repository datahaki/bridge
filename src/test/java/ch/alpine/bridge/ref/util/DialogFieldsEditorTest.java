// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.lang.Consumers;
import showcase.data.GuiExtension;

class DialogFieldsEditorTest {
  @Test
  void testShow() {
    DialogFieldsEditor dialogFieldsEditor = DialogFieldsEditor.show(null, new GuiExtension(), "here", Consumers.empty());
    dialogFieldsEditor.getSelection();
    dialogFieldsEditor.dispose();
  }
}
