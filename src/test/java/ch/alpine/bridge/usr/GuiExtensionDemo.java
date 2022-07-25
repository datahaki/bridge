// code by jph
package ch.alpine.bridge.usr;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ContainerEnabler;
import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.mat.re.Pivots;

public class GuiExtensionDemo {
  private final GuiExtension guiExtension = new GuiExtension();
  public final JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
  private final JFrame jFrame = new JFrame();

  public GuiExtensionDemo() {
    guiExtension.cdg = null;
    guiExtension.background = null;
    guiExtension.font = null;
    guiExtension.date = null;
    guiExtension.time = null;
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // the code below demonstrates that individual listeners can be attached
    for (FieldPanel fieldPanel : panelFieldsEditor.list()) {
      FieldWrap fieldWrap = fieldPanel.fieldWrap();
      if (fieldWrap.getField().getName().startsWith("s")) {
        fieldPanel.addListener(s -> {
          System.out.println(fieldWrap.getField().getName() + " = " + s);
        });
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
        jButton.addActionListener(l -> {
          guiExtension.fuse = false;
          objectPropertiesArea.update();
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JCheckBox jCheckBox = new JCheckBox("disable");
        jCheckBox.addActionListener(a -> ContainerEnabler.setEnabled(jSplitPane, !jCheckBox.isSelected()));
        jToolBar.add(jCheckBox);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    jSplitPane.setDividerLocation(500);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 100, 500, 900);
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    // FieldsEditorParam.GLOBAL.componentMinHeight_override = true;
    // FieldsEditorParam.GLOBAL.componentMinHeight = RealScalar.of(32);
    // ---
    // FieldsEditorParam.GLOBAL.textFieldFont_override = true;
    // FieldsEditorParam.GLOBAL.textFieldFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
    // ---
    // FieldsEditorParam.GLOBAL.labelFont_override = true;
    // FieldsEditorParam.GLOBAL.labelFont = new Font(Font.SERIF, Font.PLAIN, 13);
    // ---
    // FieldsEditorParam.GLOBAL.checkBoxIcon_override = true;
    // FieldsEditorParam.GLOBAL.checkBoxIcon = CheckBoxIcon.LEDGREEN;
    // FieldsEditorParam.GLOBAL.checkBoxIconSize = RealScalar.of(32);
    // ---
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    guiExtensionDemo.jFrame.setVisible(true);
  }
}
