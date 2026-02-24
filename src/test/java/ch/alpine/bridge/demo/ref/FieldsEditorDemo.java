// code by jph
package ch.alpine.bridge.demo.ref;

import ch.alpine.bridge.ref.data.MyConfig;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;

class FieldsEditorDemo {
  static void main() {
    DialogFieldsEditor.show(null, new MyConfig(), "here");
  }
}
