// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.FontDialog;

/* package */ class FontPanel extends DialogPanel {
  public FontPanel(FieldWrap fieldWrap, Font font) {
    super(fieldWrap, font);
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    return new FontDialog(component, (Font) value, font -> getJTextField().setText(fieldWrap().toString(font)));
  }
}
