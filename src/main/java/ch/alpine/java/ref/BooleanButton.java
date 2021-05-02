// code by jph
package ch.alpine.java.ref;

import javax.swing.JButton;
import javax.swing.JComponent;

import ch.alpine.java.ref.gui.FieldPanel;

/* package */ class BooleanButton extends FieldPanel {
  private final JButton jButton;

  public BooleanButton(FieldWrap fieldWrap, String text) {
    super(fieldWrap);
    jButton = new JButton(text);
    jButton.addActionListener(event -> notifyListeners("true"));
  }

  @Override
  public JComponent getJComponent() {
    return jButton;
  }
}
