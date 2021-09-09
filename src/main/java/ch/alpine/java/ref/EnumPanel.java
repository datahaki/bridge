// code by jph
package ch.alpine.java.ref;

import javax.swing.JComponent;

import ch.alpine.java.awt.SpinnerLabel;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel = new SpinnerLabel<>();

  public EnumPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    spinnerLabel.setFont(FONT);
    spinnerLabel.setArray(objects);
    spinnerLabel.setValueSafe(object);
    spinnerLabel.addSpinnerListener(value -> notifyListeners(fieldWrap.toString(value)));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return spinnerLabel;
  }
}
