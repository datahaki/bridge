// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.CheckBoxIcon;

class PanelFieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    JFrame jFrame = new JFrame();
    JTabbedPane jTabbedPane = new JTabbedPane();
    jTabbedPane.addTab("nested", PanelFieldsEditor.nested(new GuiExtension()).createJScrollPane());
    jTabbedPane.addTab("splits", PanelFieldsEditor.splits(new GuiExtension()).createJScrollPane());
    jTabbedPane.addTab("single", PanelFieldsEditor.single(new GuiExtension()).createJScrollPane());
    jTabbedPane.addTab("toolba", PanelFieldsEditor.toolbar(new GuiExtension()).createJScrollPane());
    {
      GuiExtension guiExtension = new GuiExtension();
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      ToolbarFieldsEditor.addToComponent(guiExtension, jToolBar);
      // ---
      JPanel jPanel = new JPanel(new BorderLayout());
      jPanel.add(new JScrollPane(jToolBar, //
          ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.NORTH);
      jTabbedPane.addTab("toolbar", jPanel);
    }
    jFrame.setContentPane(jTabbedPane);
    return jFrame;
  }

  static void main() {
    // FieldsEditorParam.GLOBAL.textFieldFont_override = true;
    // FieldsEditorParam.GLOBAL.textFieldFont = new Font(Font.MONOSPACED, Font.BOLD, 22);
    // ---
    // FieldsEditorParam.GLOBAL.labelFont_override = true;
    // FieldsEditorParam.GLOBAL.labelFont = new Font(Font.SERIF, Font.PLAIN, 13);
    // ---
    FieldsEditorParam.GLOBAL.checkBoxParam.override = true;
    FieldsEditorParam.GLOBAL.checkBoxParam.icon = CheckBoxIcon.LEDGREEN;
    FieldsEditorParam.GLOBAL.checkBoxParam.size = 32;
    // ---
    new PanelFieldsEditorDemo().runStandalone();
  }
}
