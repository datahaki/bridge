// code by jph
package test.demo;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.bridge.util.WindowSupplier;
import test.data.GuiExtension;

class PanelFieldsEditorDemo implements WindowSupplier {
  private final JFrame jFrame = new JFrame();

  public PanelFieldsEditorDemo() {
    JPanel jPanel = new JPanel(new GridLayout(1, 3));
    {
      jPanel.add(PanelFieldsEditor.nested(new GuiExtension()).createJScrollPane());
      jPanel.add(PanelFieldsEditor.splits(new GuiExtension()).createJScrollPane());
      jPanel.add(PanelFieldsEditor.single(new GuiExtension()).createJScrollPane());
    }
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(100, 100, 1800, 1100);
  }

  @Override
  public Window createWindow() {
    return jFrame;
  }

  static void main() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    // ---
    // FieldsEditorParam.GLOBAL.textFieldFont_override = true;
    // FieldsEditorParam.GLOBAL.textFieldFont = new Font(Font.MONOSPACED, Font.BOLD, 22);
    // ---
    // FieldsEditorParam.GLOBAL.labelFont_override = true;
    // FieldsEditorParam.GLOBAL.labelFont = new Font(Font.SERIF, Font.PLAIN, 13);
    // ---
    // FieldsEditorParam.GLOBAL.checkBoxIcon_override = true;
    // FieldsEditorParam.GLOBAL.checkBoxIcon = CheckBoxIcon.LEDGREEN;
    // FieldsEditorParam.GLOBAL.checkBoxIconSize = RealScalar.of(32);
    // ---
    new PanelFieldsEditorDemo().run();
  }
}
