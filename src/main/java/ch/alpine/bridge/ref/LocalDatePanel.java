// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalDate;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.LocalDateDialog;

class LocalDatePanel extends DialogPanel {
  public LocalDatePanel(FieldWrap fieldWrap, LocalDate localDate) {
    super(fieldWrap, localDate);
    // ---
    JComponent jTextField = getTextFieldComponent();
    jTextField.addMouseWheelListener(mouseWheelEvent -> {
      // TODO IMPL not clean due to object vs. string
      Object object = fieldWrap.toValue(getText());
      if (Objects.nonNull(object)) {
        LocalDate current = (LocalDate) object;
        current = current.plusDays(mouseWheelEvent.getWheelRotation());
        String string = current.toString();
        setText(string);
        indicateGui();
        nofifyIfValid(string);
      }
    });
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
