// code by jph
package ch.alpine.bridge.ref;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.alpine.bridge.ref.util.FieldsEditor;

/* package */ class ObjectPropertiesArea {
  private final JTextArea jTextArea = new JTextArea();
  private final Object object;

  public ObjectPropertiesArea(FieldsEditor fieldsEditor, Object object) {
    this.object = object;
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    update();
    fieldsEditor.addUniversalListener(this::update);
  }

  public JComponent createJComponent() {
    return new JScrollPane(jTextArea);
  }

  public void update() {
    jTextArea.setText(ObjectProperties.save(object));
    jTextArea.setCaretPosition(0);
  }
}
