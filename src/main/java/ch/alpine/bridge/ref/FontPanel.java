// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.awt.Font;
import java.util.Objects;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.FontDialog;

/* package */ class FontPanel extends DialogPanel {
  private static final Font FALLBACK = new Font(Font.DIALOG, Font.PLAIN, 12);

  public FontPanel(FieldWrap fieldWrap, Font font) {
    super(fieldWrap, font);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    Font fallback = Objects.isNull(value) ? FALLBACK : (Font) value;
    return new FontDialog(component, fallback, this::updateAndNotify);
  }
}
