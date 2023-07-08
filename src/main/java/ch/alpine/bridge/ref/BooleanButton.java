// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/* package */ final class BooleanButton extends FieldPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());

  public BooleanButton(FieldWrap fieldWrap, String text) {
    super(fieldWrap);
    JButton jButton = new JButton(text);
    jButton.addActionListener(event -> notifyListeners(BooleanParser.TRUE));
    jPanel.add(BorderLayout.WEST, jButton);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    Objects.requireNonNull(value);
  }
}
