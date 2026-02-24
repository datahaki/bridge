// code by jph
package ch.alpine.bridge.demo.ref;

import ch.alpine.bridge.ref.data.TemplateEnumParam;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;

class TemplateEnumDemo {
  static void main() {
    TemplateEnumParam templateEnumParam = new TemplateEnumParam();
    DialogFieldsEditor.show(null, templateEnumParam, "title");
  }
}
