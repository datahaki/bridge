// code by jph
package ch.alpine.java.ref;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/* package */ class TestHelper {
  final JPanel jPanel = new JPanel(new BorderLayout());
  final Runnable runnable;

  public TestHelper(PanelFieldsEditor panelFieldsEditor, Object object) {
    jPanel.add(BorderLayout.NORTH, panelFieldsEditor.createJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    {
      runnable = () -> jTextArea.setText(ObjectProperties.string(object));
      runnable.run();
      panelFieldsEditor.addUniversalListener(runnable);
    }
    jPanel.add(BorderLayout.CENTER, jTextArea);
  }
}