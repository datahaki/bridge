// code by jph, gjoel
package ch.alpine.java.ref;

import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/* package */ class BooleanCheckBox extends FieldPanel {
  private final JCheckBox jCheckBox = new JCheckBox();

  public BooleanCheckBox(FieldWrap fieldWrap, Boolean value) {
    super(fieldWrap);
    // ---
    jCheckBox.setIcon(FieldsEditorManager.getIcon(FieldsEditorKey.ICON_CHECKBOX_0));
    jCheckBox.setSelectedIcon(FieldsEditorManager.getIcon(FieldsEditorKey.ICON_CHECKBOX_1));
    jCheckBox.setBorder(null);
    // ---
    jCheckBox.setOpaque(false);
    if (Objects.nonNull(value))
      jCheckBox.setSelected(value);
    jCheckBox.addActionListener(event -> notifyListeners(getText()));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jCheckBox;
  }

  private String getText() {
    return fieldWrap().toString(jCheckBox.isSelected());
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    jCheckBox.setSelected((Boolean) value);
  }
}
