// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.swing.LookAndFeels;

public enum ToolbarFieldsEditorDemo {
  ;
  public static void main(String[] args) throws Exception {
    LookAndFeels.DARK.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    JToolBar jToolBar = new JToolBar();
    jToolBar.setFloatable(false);
    FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(guiExtension, jToolBar);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.NORTH, new JScrollPane(jToolBar, //
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(fieldsEditor, guiExtension);
    jPanel.add(BorderLayout.CENTER, objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(50, 200, 1500, 300);
    jFrame.setVisible(true);
  }
}
