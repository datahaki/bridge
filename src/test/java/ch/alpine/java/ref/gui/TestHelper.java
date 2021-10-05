// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ch.alpine.java.ref.ObjectProperties;

/* package */ enum TestHelper {
  ;
  public static JComponent fieldsAndTextArea(PanelFieldsEditor panelFieldsEditor, Object object) {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("North", panelFieldsEditor.getJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    {
      Runnable runnable = () -> jTextArea.setText(ObjectProperties.string(object));
      runnable.run();
      panelFieldsEditor.addUniversalListener(runnable);
    }
    jPanel.add("Center", jTextArea);
    return jPanel;
  }
}
