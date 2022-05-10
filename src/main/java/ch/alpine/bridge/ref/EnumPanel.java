// code by jph, gjoel
package ch.alpine.bridge.ref;

import javax.swing.JComponent;

import ch.alpine.bridge.swing.SpinnerLabel;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel = new SpinnerLabel<>();

  public EnumPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    spinnerLabel.setFont(FieldsEditorManager.getFont(FieldsEditorKey.FONT_TEXTFIELD));
    spinnerLabel.setArray(objects);
    spinnerLabel.updatePreferredSize();
    spinnerLabel.setValueSafe(object);
    spinnerLabel.addSpinnerListener(value -> notifyListeners(fieldWrap.toString(value)));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return spinnerLabel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    spinnerLabel.setValue(value);
  }
}