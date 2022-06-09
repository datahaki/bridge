// code by jph, gjoel
package ch.alpine.bridge.ref;

import javax.swing.JComponent;

import ch.alpine.bridge.swing.SpinnerLabel;

/* package */ class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel;

  public EnumPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    spinnerLabel = SpinnerLabel.of(objects);
    spinnerLabel.setFont(FieldsEditorManager.getFont(FieldsEditorKey.FONT_TEXTFIELD));
    spinnerLabel.updatePreferredSize();
    spinnerLabel.setValue(object); // throws an Exception if object is null (deliberate)
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
