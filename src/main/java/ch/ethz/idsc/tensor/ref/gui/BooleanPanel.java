// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import ch.ethz.idsc.tensor.ref.FieldType;

public class BooleanPanel extends FieldPanel {
  private final JCheckBox jCheckBox;

  public BooleanPanel(FieldType fieldType, Boolean value) {
    super(fieldType);
    jCheckBox = new JCheckBox();
    if (Objects.nonNull(value))
      jCheckBox.setSelected(value);
    jCheckBox.addActionListener(event -> notifyListeners(getText()));
  }

  @Override
  public JComponent getJComponent() {
    return jCheckBox;
  }

  private String getText() {
    return "" + jCheckBox.isSelected();
  }
}
