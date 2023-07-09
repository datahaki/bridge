// code by jph
package ch.alpine.bridge.usr;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

public class PanelFieldsEditorDemo {
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

  public static void main(String[] args) {
    LookAndFeels.GTK_PLUS.updateComponentTreeUI();
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
    PanelFieldsEditorDemo guiExtensionDemo = new PanelFieldsEditorDemo();
    guiExtensionDemo.jFrame.setVisible(true);
  }
}
