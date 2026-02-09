// code by jph
package showcase.ref;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import showcase.data.GuiExtension;

/** the components' height are elevated probably because of the slider */
class ToolbarFieldsEditorDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    LookAndFeels.DARK.updateComponentTreeUI();
    GuiExtension guiExtension = new GuiExtension();
    JToolBar jToolBar = new JToolBar();
    jToolBar.setFloatable(false);
    FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(guiExtension, jToolBar);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(new JScrollPane(jToolBar, //
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.NORTH);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(fieldsEditor, guiExtension);
    jPanel.add(objectPropertiesArea.createJComponent(), BorderLayout.CENTER);
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(50, 200, 1500, 300);
    return jFrame;
  }

  static void main() {
    new ToolbarFieldsEditorDemo().run();
  }
}
