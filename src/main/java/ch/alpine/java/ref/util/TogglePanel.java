// code by jph, gjoel
package ch.alpine.java.ref.util;

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;

/** toggle button substitution as substitution for checkbox for boolean fields */
/* package */ class TogglePanel extends FieldPanel {
  private final JToggleButton jToggleButton;

  public TogglePanel(FieldWrap fieldWrap, String text, Boolean value) {
    super(fieldWrap);
    jToggleButton = new JToggleButton(text);
    if (Objects.nonNull(value))
      jToggleButton.setSelected(value);
    jToggleButton.addActionListener(event -> notifyListeners(getText()));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jToggleButton;
  }

  private String getText() {
    return fieldWrap().toString(jToggleButton.isSelected());
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    jToggleButton.setSelected((Boolean) value);
  }
}
