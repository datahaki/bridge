// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ContainerDescent;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.mat.re.Pivots;

class GuiExtensionDemo implements WindowProvider {
  @Override
  public JFrame getWindow() {
    GuiExtension guiExtension = new GuiExtension();
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JFrame jFrame = new JFrame();
    guiExtension.cdg = null;
    guiExtension.background = null;
    guiExtension.font = null;
    guiExtension.date = null;
    guiExtension.time = null;
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(guiExtension);
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // the code below demonstrates that individual listeners can be attached
    for (FieldPanel fieldPanel : panelFieldsEditor.list()) {
      FieldWrap fieldWrap = fieldPanel.fieldWrap();
      if (fieldWrap.getField().getName().startsWith("s")) {
        fieldPanel.addListener(s -> System.out.println(fieldWrap.getField().getName() + " = " + s));
      }
    }
    jSplitPane.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    jSplitPane.add(objectPropertiesArea.createJComponent());
    // ---
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(jSplitPane, BorderLayout.CENTER);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      {
        JButton jButton = new JButton("reset fuse");
        jButton.addActionListener(_ -> {
          guiExtension.fuse = false;
          objectPropertiesArea.update();
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JCheckBox jCheckBox = new JCheckBox("disable");
        jCheckBox.addActionListener(_ -> ContainerDescent.setEnabled(jSplitPane, !jCheckBox.isSelected()));
        jToolBar.add(jCheckBox);
      }
      jToolBar.addSeparator();
      {
        JCheckBox jCheckBox = new JCheckBox("disable root");
        jCheckBox.addActionListener(_ -> ContainerDescent.setEnabled(jFrame.getRootPane(), !jCheckBox.isSelected()));
        jToolBar.add(jCheckBox);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    jSplitPane.setDividerLocation(500);
    jFrame.setContentPane(jPanel);
    return jFrame;
  }

  static void main() {
    FieldsEditorParam.GLOBAL.componentMinSize_override = true;
    FieldsEditorParam.GLOBAL.componentMinSize = 40;
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
    new GuiExtensionDemo().runStandalone();
  }
}
