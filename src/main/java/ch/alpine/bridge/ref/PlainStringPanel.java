// code by jph
package ch.alpine.bridge.ref;

import javax.swing.JComponent;

/* package */ final class PlainStringPanel extends StringPanel {
  public PlainStringPanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap, value);
  }

  @Override
  public JComponent getJComponent() {
    return getTextFieldComponent();
  }
}
