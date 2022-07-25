// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalDate;
import java.util.Objects;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.LocalDateDialog;

/* package */ class LocalDatePanel extends DialogPanel {
  public LocalDatePanel(FieldWrap fieldWrap, LocalDate localDate) {
    super(fieldWrap, localDate);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    LocalDate fallback = Objects.isNull(value) ? LocalDate.now() : (LocalDate) value;
    LocalDateDialog localDateDialog = new LocalDateDialog(fallback) {
      @Override
      public void selection(LocalDate current) {
        updateAndNotify(current);
      }
    };
    return DialogBuilder.create(component, localDateDialog);
  }
}
