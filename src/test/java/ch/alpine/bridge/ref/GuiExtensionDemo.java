// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ResourceData;

public enum GuiExtensionDemo {
  ;
  public static void main(String[] args) throws Exception {
    int n = 24;
    String folder = "/image/checkbox/ballot/";
    FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "0.png"), n, n)));
    FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "1.png"), n, n)));
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    jGrid.add(objectPropertiesArea.getJComponent());
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jGrid);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        objectPropertiesArea.run();
      });
      jPanel.add(BorderLayout.SOUTH, jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
