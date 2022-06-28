// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/* package */ final class BooleanButton extends FieldPanel {
  private final JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

  public BooleanButton(FieldWrap fieldWrap, String text) {
    super(fieldWrap);
    JButton jButton = new JButton(text);
    {
      FieldsEditorManager.establish(FieldsEditorKey.INT_BUTTON_HEIGHT, jButton);
    }
    jButton.addActionListener(event -> notifyListeners(BooleanParser.TRUE));
    jPanel.add(jButton);
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
