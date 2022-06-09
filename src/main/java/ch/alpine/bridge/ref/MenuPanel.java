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
        StandardMenu standardMenu = new StandardMenu() {
          @Override
          protected void design(JPopupMenu jPopupMenu) {
            for (String string : supplier.get()) {
              JMenuItem jMenuItem = new JMenuItem(string);
              jMenuItem.setFont(getJTextField().getFont());
              FieldsEditorManager.establish(FieldsEditorKey.INT_STRING_PANEL_HEIGHT, jMenuItem);
              jMenuItem.addActionListener(event -> {
                getJTextField().setText(string);
                indicateGui();
                nofifyIfValid(string);
              });
              // TODO BRIDGE here the item is not yet selected as in SpinnerMenu...
              jPopupMenu.add(jMenuItem);
            }
          }
        };
        standardMenu.south(jButton);
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
