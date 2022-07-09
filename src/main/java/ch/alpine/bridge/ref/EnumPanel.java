// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.JComponent;

import ch.alpine.bridge.swing.SpinnerLabel;

/* package */ final class EnumPanel extends FieldPanel {
  private final SpinnerLabel<Object> spinnerLabel;

  public EnumPanel(FieldWrap fieldWrap, Object object, Supplier<List<Object>> supplier) {
    super(fieldWrap);
    spinnerLabel = SpinnerLabel.of(supplier);
    spinnerLabel.setFont(FieldsEditorParam.GLOBAL.textFieldFont.toFont());
    spinnerLabel.updatePreferredSize();
    if (Objects.nonNull(object))
      spinnerLabel.setValue(object);
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
