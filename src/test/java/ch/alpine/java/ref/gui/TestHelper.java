// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import ch.alpine.java.ref.ObjectProperties;

/* package */ class TestHelper {
  final JPanel jPanel = new JPanel(new BorderLayout());
  final Runnable runnable;

  public TestHelper(PanelFieldsEditor panelFieldsEditor, Object object) {
    jPanel.add("North", panelFieldsEditor.getJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    {
      runnable = () -> jTextArea.setText(ObjectProperties.string(object));
      runnable.run();
      panelFieldsEditor.addUniversalListener(runnable);
    }
    jPanel.add("Center", jTextArea);
  }
}
