// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorKey;
import ch.alpine.bridge.ref.FieldsEditorManager;
import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.swing.LookAndFeels;

public enum GuiExtensionDemo {
  ;
  public static void main(String[] args) throws Exception {
    // int n = 24;
    // String folder = "/ch/alpine/bridge/ref/checkbox/ballot/";
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "0.png"), n, n)));
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "1.png"), n, n)));
    // LookAndFeels.GTK_PLUS.updateUI();
    LookAndFeels.DARK.updateUI();
    FieldsEditorManager.set(FieldsEditorKey.FONT_TEXTFIELD, new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
    // ---
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.cdg = null;
    // guiExtension.pivots = ;
    // guiExtension.pivots2 = null;
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
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
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    jGrid.add(objectPropertiesArea.createJComponent());
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jGrid);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        objectPropertiesArea.update();
      });
      jPanel.add(BorderLayout.SOUTH, jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
