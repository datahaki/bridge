// code by jph, gjoel
package ch.alpine.java.ref;

import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/* package */ class BooleanPanel extends FieldPanel {
  private final JCheckBox jCheckBox = new JCheckBox();

  public BooleanPanel(FieldWrap fieldWrap, Boolean value) {
    super(fieldWrap);
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
