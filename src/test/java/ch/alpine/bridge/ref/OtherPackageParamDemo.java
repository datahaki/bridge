// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.OtherPackageParam;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

public enum OtherPackageParamDemo {
  ;
  public static void main(String[] args) throws Exception {
    // int n = 24;
    // String folder = "/ch/alpine/bridge/ref/checkbox/ballot/";
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "0.png"), n, n)));
    // FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "1.png"), n, n)));
    LookAndFeels.GTK_PLUS.updateUI();
    // ---
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    // guiExtension.pivots = ;
    // guiExtension.pivots2 = null;
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(otherPackageParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, otherPackageParam);
    jGrid.add(objectPropertiesArea.createJComponent());
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jGrid);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
