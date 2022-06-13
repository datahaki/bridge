// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public enum EmptyDemo {
  ;
  public static void main(String[] args) throws Exception {
    EmptyParam emptyParam = new EmptyParam();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(emptyParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, emptyParam);
    jGrid.add(objectPropertiesArea.createJComponent());
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jGrid);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
