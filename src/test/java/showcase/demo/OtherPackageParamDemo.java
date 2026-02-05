// code by jph
package showcase.demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.lang.WindowProvider;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import showcase.data.OtherPackageParam;

class OtherPackageParamDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    // ---
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(otherPackageParam);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, otherPackageParam);
    jGrid.add(objectPropertiesArea.createJComponent());
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(jGrid, BorderLayout.CENTER);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    return jFrame;
  }

  static void main() {
    new OtherPackageParamDemo().run();
  }
}
