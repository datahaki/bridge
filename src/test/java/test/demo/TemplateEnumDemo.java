package test.demo;

import java.awt.Window;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.util.WindowSupplier;
import test.data.TemplateEnumParam;

class TemplateEnumDemo implements WindowSupplier {
  @Override
  public Window createWindow() {
    TemplateEnumParam templateEnumParam = new TemplateEnumParam();
    return DialogFieldsEditor.show(null, templateEnumParam, "title");
  }

  static void main() {
    new TemplateEnumDemo().run();
  }
}
