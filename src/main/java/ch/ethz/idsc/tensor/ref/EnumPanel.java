// code by jph
package ch.ethz.idsc.tensor.ref;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ch.ethz.idsc.java.awt.SpinnerLabel;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel = new SpinnerLabel<>();

  public EnumPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    JLabel jLabel = spinnerLabel.getLabelComponent();
    jLabel.setFont(FieldPanel.FONT);
    jLabel.setHorizontalAlignment(SwingConstants.LEFT);
    spinnerLabel.setArray(objects);
    spinnerLabel.setValueSafe(object);
    spinnerLabel.addSpinnerListener(value -> notifyListeners(fieldWrap.toString(value)));
  }

  @Override
  public JComponent getJComponent() {
    return spinnerLabel.getLabelComponent();
  }
}
