// code by jph
package showcase.demo;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import showcase.data.SimpleParam;

class AnotherPanelFieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.lookAndFeels = LookAndFeels.DARK;
    simpleParam.lookAndFeels.updateComponentTreeUI();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(simpleParam);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, simpleParam);
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jGrid);
    jFrame.setBounds(100, 100, 500, 900);
    return jFrame;
  }

  static void main() {
    new AnotherPanelFieldsEditorDemo().run();
  }
}
