// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.javax.swing.LookAndFeels;

public enum GuiExtensionDemo {
  ;
  public static void main(String[] args) throws Exception {
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsEditor = new PanelFieldsEditor(guiExtension);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    TestHelper testHelper = new TestHelper(fieldsEditor, guiExtension);
    // ---
    JFrame jFrame = new JFrame();
    // File root = GrzSettings.file("GuiExtension");
    // root.mkdirs();
    // WindowConfiguration.attach(jFrame, new File(root, "WindowConfiguration.properties"));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, testHelper.jPanel);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        testHelper.runnable.run();
        // fieldsEditor.list().forEach(fp->fp.notifyListeners(""));
      });
      jPanel.add("South", jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
