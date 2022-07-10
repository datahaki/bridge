// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ex.SimpleParam;
import ch.alpine.bridge.swing.LookAndFeels;

public enum PanelFieldsEditorDemo {
  ;
  public static void main(String[] args) throws Exception {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.lookAndFeels = LookAndFeels.LIGHT;
    simpleParam.lookAndFeels.updateComponentTreeUI();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(simpleParam);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, simpleParam);
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jGrid);
    jFrame.setBounds(100, 100, 500, 900);
    jFrame.setVisible(true);
  }
}
