// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ch.ethz.idsc.java.awt.SpinnerLabel;
import ch.ethz.idsc.tensor.ref.FieldType;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel = new SpinnerLabel<>();

  public EnumPanel(Object[] objects, Object object) {
    JLabel jLabel = spinnerLabel.getLabelComponent();
    jLabel.setFont(FieldPanel.FONT);
    jLabel.setHorizontalAlignment(SwingConstants.LEFT);
    spinnerLabel.setArray(objects);
    spinnerLabel.setValueSafe(object);
    spinnerLabel.addSpinnerListener(value -> notifyListeners(FieldType.ENUM.toString(value)));
  }

  @Override
  public JComponent getJComponent() {
    return spinnerLabel.getLabelComponent();
  }
}
