// code by jph
package ch.alpine.curios.ref;

import java.awt.Window;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.TemplateEnumParam;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;

class TemplateEnumDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    TemplateEnumParam templateEnumParam = new TemplateEnumParam();
    return DialogFieldsEditor.show(null, templateEnumParam, "title");
  }

  static void main() {
    new TemplateEnumDemo().run();
  }
}
