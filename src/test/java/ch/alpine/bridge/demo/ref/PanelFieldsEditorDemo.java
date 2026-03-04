// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.CheckBoxIcon;

class PanelFieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    JFrame jFrame = new JFrame();
    JTabbedPane jTabbedPane = new JTabbedPane();
    jTabbedPane.addTab("nested", PanelFieldsEditor.nested(new GuiExtension()).createJScrollPane());
    jTabbedPane.addTab("splits", PanelFieldsEditor.splits(new GuiExtension()).createJScrollPane());
    jTabbedPane.addTab("single", PanelFieldsEditor.single(new GuiExtension()).createJScrollPane());
    jFrame.setContentPane(jTabbedPane);
    return jFrame;
  }

  static void main() {
    FieldsEditorParam.GLOBAL.textFieldFont_override = true;
    FieldsEditorParam.GLOBAL.textFieldFont = new Font(Font.MONOSPACED, Font.BOLD, 22);
    // ---
    FieldsEditorParam.GLOBAL.labelFont_override = true;
    FieldsEditorParam.GLOBAL.labelFont = new Font(Font.SERIF, Font.PLAIN, 13);
    // ---
    FieldsEditorParam.GLOBAL.checkBoxParam.override = true;
    FieldsEditorParam.GLOBAL.checkBoxParam.icon = CheckBoxIcon.LEDGREEN;
    FieldsEditorParam.GLOBAL.checkBoxParam.size = 32;
    // ---
    new PanelFieldsEditorDemo().runStandalone();
  }
}
