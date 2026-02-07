// code by jph
package showcase.demo;

import java.awt.Window;

import ch.alpine.bridge.lang.WindowProvider;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import showcase.data.TemplateEnumParam;

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
