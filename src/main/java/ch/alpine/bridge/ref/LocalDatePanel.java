// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalDate;
import java.util.Objects;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.LocalDateDialog;

/* package */ class LocalDatePanel extends DialogPanel {
  private static final LocalDate FALLBACK = LocalDate.now();

  public LocalDatePanel(FieldWrap fieldWrap, LocalDate localDate) {
    super(fieldWrap, localDate);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    LocalDate fallback = Objects.isNull(value) ? FALLBACK : (LocalDate) value;
    return new LocalDateDialog(component, fallback, this::updateAndNotify);
  }
}
