// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.alpine.bridge.swing.SelectionMenu;

/* package */ class MenuPanel extends StringPanel {
  private static final String BUTTON_TEXT = "?";
  // ---
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(BUTTON_TEXT);

  /** @param fieldWrap
   * @param object
   * @param supplier invoked when menu button "?" is pressed */
  public MenuPanel(FieldWrap fieldWrap, Object object, Supplier<List<String>> supplier) {
    super(fieldWrap, object);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JTextField jTextField = getJTextField();
        SelectionMenu<String> spinnerMenu = new SelectionMenu<>(supplier.get(), jTextField.getText(), jTextField.getFont(), false) {
          @Override
          public void update(String string) {
            jTextField.setText(string);
            indicateGui();
            nofifyIfValid(string);
          }
        };
        spinnerMenu.showRight(jButton);
      }
    });
    jPanel.add(BorderLayout.CENTER, getJTextField());
    jPanel.add(BorderLayout.EAST, jButton);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }
}
