// code by jph
package ch.ethz.idsc.tensor.ref;

import javax.swing.JButton;
import javax.swing.JComponent;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

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
