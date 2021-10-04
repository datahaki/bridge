// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ch.alpine.java.ref.ObjectProperties;

enum TestHelper {
  ;
  public static JComponent fieldsAndTextArea(FieldsPanel fieldsEditor, Object object) {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("North", fieldsEditor.getJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    {
      Runnable runnable = () -> jTextArea.setText(ObjectProperties.string(object));
      runnable.run();
      fieldsEditor.addUniversalListener(runnable);
    }
    jPanel.add("Center", jTextArea);
    return jPanel;
  }
}
