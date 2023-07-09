// code by jph, gjoel
package ch.alpine.bridge.usr;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

public enum GuiExtensionSynced {
  ;
  public static void main(String[] args) {
    LookAndFeels.INTELLI_J.updateComponentTreeUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor left_FieldsEditor = PanelFieldsEditor.splits(guiExtension);
    PanelFieldsEditor rightFieldsEditor = PanelFieldsEditor.splits(guiExtension);
    left_FieldsEditor.addUniversalListener(() -> System.out.println("left_ changed"));
    rightFieldsEditor.addUniversalListener(() -> System.out.println("right changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
    for (PanelFieldsEditor fieldsEditor : new PanelFieldsEditor[] { left_FieldsEditor, rightFieldsEditor }) {
      ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(fieldsEditor, guiExtension);
      JPanel jGrid = new JPanel(new GridLayout(2, 1));
      jGrid.add(fieldsEditor.createJScrollPane());
      jGrid.add(objectPropertiesArea.createJComponent());
      JPanel jPanel = new JPanel(new BorderLayout());
      jPanel.add(jGrid, BorderLayout.CENTER);
      {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        {
          JButton jButton = new JButton("reset fuse");
          jButton.addActionListener(event -> {
            guiExtension.fuse = false;
            objectPropertiesArea.update();
          });
          buttonPanel.add(jButton);
        }
        {
          JButton jButton = new JButton("sync");
          jButton.addActionListener(event -> {
            guiExtension.rectangle = jFrame.getBounds();
            fieldsEditor.updateJComponents();
            objectPropertiesArea.update();
          });
          buttonPanel.add(jButton);
        }
        jPanel.add(buttonPanel, BorderLayout.SOUTH);
      }
      content.add(jPanel);
    }
    jFrame.setContentPane(content);
    jFrame.setBounds(500, 200, 750, 800);
    jFrame.setVisible(true);
  }
}
