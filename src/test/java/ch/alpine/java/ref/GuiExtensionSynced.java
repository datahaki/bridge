// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.javax.swing.LookAndFeels;

public enum GuiExtensionSynced {
  ;
  public static void main(String[] args) throws Exception {
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor left_FieldsEditor = new PanelFieldsEditor(guiExtension);
    PanelFieldsEditor rightFieldsEditor = new PanelFieldsEditor(guiExtension);
    left_FieldsEditor.addUniversalListener(() -> System.out.println("left_ changed"));
    rightFieldsEditor.addUniversalListener(() -> System.out.println("right changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
    for (PanelFieldsEditor fieldsEditor : new PanelFieldsEditor[] { left_FieldsEditor, rightFieldsEditor }) {
      TestHelper testHelper = new TestHelper(fieldsEditor, guiExtension);
      JPanel jPanel = new JPanel(new BorderLayout());
      jPanel.add(BorderLayout.CENTER, testHelper.jPanel);
      {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        {
          JButton jButton = new JButton("reset fuse");
          jButton.addActionListener(event -> {
            guiExtension.fuse = false;
            testHelper.runnable.run();
          });
          buttonPanel.add(jButton);
        }
        {
          JButton jButton = new JButton("sync");
          jButton.addActionListener(event -> fieldsEditor.updateJComponents());
          jButton.addActionListener(event -> testHelper.runnable.run());
          buttonPanel.add(jButton);
        }
        jPanel.add(BorderLayout.SOUTH, buttonPanel);
      }
      content.add(jPanel);
    }
    jFrame.setContentPane(content);
    jFrame.setBounds(500, 200, 750, 800);
    jFrame.setVisible(true);
  }
}
