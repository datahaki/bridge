// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalTime;
import java.util.Objects;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.LocalTimeDialog;

/* package */ class LocalTimePanel extends DialogPanel {
  private static final LocalTime FALLBACK = LocalTime.MIDNIGHT;

  public LocalTimePanel(FieldWrap fieldWrap, LocalTime localTime) {
    super(fieldWrap, localTime);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    LocalTime fallback = Objects.isNull(value) ? FALLBACK : (LocalTime) value;
    return new LocalTimeDialog(component, fallback, this::updateAndNotify);
  }
}
