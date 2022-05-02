// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import ch.alpine.bridge.swing.StandardMenu;

/* package */ class MenuPanel extends StringPanel {
  private static final String BUTTON_TEXT = "?";
  // ---
  private final JButton jButton = new JButton(BUTTON_TEXT);

  /** @param fieldWrap
   * @param object
   * @param supplier invoked when menu button "?" is pressed */
  public MenuPanel(FieldWrap fieldWrap, Object object, Supplier<List<String>> supplier) {
    super(fieldWrap, object);
    // SpinnerLabel.updatePreferredSize(jTextField, Arrays.asList(strings));
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        StandardMenu standardMenu = new StandardMenu() {
          @Override
          protected void design(JPopupMenu jPopupMenu) {
            for (String string : supplier.get()) {
              JMenuItem jMenuItem = new JMenuItem(string);
              jMenuItem.setFont(jTextField.getFont());
              {
                // TODO try background color in menu in each l&f
                FieldsEditorManager.establish(FieldsEditorKey.INT_STRING_PANEL_HEIGHT, jMenuItem);
              }
              jMenuItem.addActionListener(event -> {
                jTextField.setText(string);
                indicateGui();
                nofifyIfValid(string);
              });
              jPopupMenu.add(jMenuItem);
            }
          }
        };
        standardMenu.south(jButton);
      }
    });
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jTextField);
    jPanel.add(BorderLayout.EAST, jButton);
    return jPanel;
  }
}
