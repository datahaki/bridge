// code by jph
package ch.ethz.idsc.tensor.ref;

import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

/* package */ class BooleanPanel extends FieldPanel {
  private final JCheckBox jCheckBox = new JCheckBox();

  public BooleanPanel(FieldWrap fieldType, Boolean value) {
    super(fieldType);
    if (Objects.nonNull(value))
      jCheckBox.setSelected(value);
    jCheckBox.addActionListener(event -> notifyListeners(getText()));
  }

  @Override
  public JComponent getJComponent() {
    return jCheckBox;
  }

  private String getText() {
    return fieldType().toString(jCheckBox.isSelected());
  }
}
