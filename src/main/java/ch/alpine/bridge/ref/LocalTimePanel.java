// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalTime;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.LocalTimeDialog;

/* package */ class LocalTimePanel extends DialogPanel {
  public LocalTimePanel(FieldWrap fieldWrap, LocalTime localTime) {
    super(fieldWrap, localTime);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    return new LocalTimeDialog(component, (LocalTime) value, localTime -> getJTextField().setText(fieldWrap().toString(localTime)));
  }
}
