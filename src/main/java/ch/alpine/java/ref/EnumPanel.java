// code by jph, gjoel
package ch.alpine.java.ref;

import javax.swing.JComponent;

import ch.alpine.javax.swing.SpinnerLabel;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel = new SpinnerLabel<>();

  public EnumPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    spinnerLabel.setFont(FieldsEditorManager.INSTANCE.getFont(FieldsEditorKey.FONT_ENUM_PANEL));
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
