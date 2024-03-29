// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalTime;
import java.util.Objects;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.LocalTimeDialog;

/* package */ class LocalTimePanel extends DialogPanel {
  public LocalTimePanel(FieldWrap fieldWrap, LocalTime localTime) {
    super(fieldWrap, localTime);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    LocalTime fallback = Objects.isNull(value) ? LocalTime.now() : (LocalTime) value;
    LocalTimeDialog localTimeDialog = new LocalTimeDialog(fallback) {
      @Override
      public void selection(LocalTime current) {
        updateAndNotify(current);
      }
    };
    return DialogBuilder.create(component, localTimeDialog);
  }
}
