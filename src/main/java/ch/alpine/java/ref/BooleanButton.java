// code by jph
package ch.alpine.java.ref;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.java.ref.gui.FieldPanel;

/* package */ class BooleanButton extends FieldPanel {
  private final JToolBar jToolBar = new JToolBar();
  private final JButton jButton;

  public BooleanButton(FieldWrap fieldWrap, String text) {
    super(fieldWrap);
    jButton = new JButton(text);
    jButton.addActionListener(event -> notifyListeners("true"));
    jToolBar.setLayout(new FlowLayout(0, 0, 0));
    jToolBar.setFloatable(false);
    jToolBar.add(jButton);
  }

  @Override
  public JComponent getJComponent() {
    return jToolBar;
  }
}
