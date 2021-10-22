// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

/* package */ class BooleanButton extends FieldPanel {
  private final JToolBar jToolBar = new JToolBar();

  public BooleanButton(FieldWrap fieldWrap, String text) {
    super(fieldWrap);
    JButton jButton = new JButton(text);
    jButton.addActionListener(event -> notifyListeners("true"));
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    jToolBar.setFloatable(false);
    jToolBar.add(jButton);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jToolBar;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    // ---
  }
}
