// code by jph
package ch.alpine.bridge.ref;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.alpine.bridge.ref.util.FieldsEditor;

/* package */ class ObjectPropertiesArea implements Runnable {
  private final JTextArea jTextArea = new JTextArea();
  private final Object object;

  public ObjectPropertiesArea(FieldsEditor fieldsEditor, Object object) {
    this.object = object;
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    run();
    fieldsEditor.addUniversalListener(this);
  }

  public JComponent getJComponent() {
    return new JScrollPane(jTextArea);
  }

  @Override
  public void run() {
    jTextArea.setText(ObjectProperties.string(object));
    jTextArea.setCaretPosition(0);
  }
}
