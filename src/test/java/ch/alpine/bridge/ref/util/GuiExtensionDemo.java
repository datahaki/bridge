// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ContainerEnabler;
import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorKey;
import ch.alpine.bridge.ref.FieldsEditorManager;
import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.swing.CheckBoxIcons;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.mat.re.Pivots;

public class GuiExtensionDemo {
  private final GuiExtension guiExtension = new GuiExtension();
  // private final
  public final JPanel jGrid = new JPanel(new GridLayout(2, 1));
  private final JFrame jFrame = new JFrame();

  public GuiExtensionDemo() {
    guiExtension.cdg = null;
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    panelFieldsEditor.updateJComponents();
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
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    jGrid.add(objectPropertiesArea.createJComponent());
    // ---
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jGrid);
    {
      JToolBar jToolBar = new JToolBar();
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
        jCheckBox.addActionListener(a -> ContainerEnabler.setEnabled(jGrid, !jCheckBox.isSelected()));
        jToolBar.add(jCheckBox);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 100, 500, 900);
  }

  public static void main(String[] args) throws Exception {
    // int n = 24;
    // String folder = "/ch/alpine/bridge/ref/checkbox/ballot/";
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "0.png"), n, n)));
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "1.png"), n, n)));
    // LookAndFeels.GTK_PLUS.updateUI();
    CheckBoxIcons.BALLIT.init(32);
    LookAndFeels.DARK.updateUI();
    FieldsEditorManager.set(FieldsEditorKey.FONT_TEXTFIELD, new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
    // ---
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    guiExtensionDemo.jFrame.setVisible(true);
  }
}
