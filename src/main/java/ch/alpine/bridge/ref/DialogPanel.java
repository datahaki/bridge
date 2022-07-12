// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import ch.alpine.bridge.awt.WindowClosed;

/* package */ abstract class DialogPanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(StaticHelper.BUTTON_TEXT);
  /** For each instance of {@link ColorPanel}, a single JColorChooser may be opened
   * by the user. The jDialog field is non-null whenever the dialog is visible. */
  private JDialog jDialog = null;

  /** @param fieldWrap
   * @param value initial and fallback */
  public DialogPanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap, value);
    jButton.addActionListener(actionEvent -> {
      if (Objects.isNull(jDialog)) {
        // fallback font is restored when "Cancel" is pressed
        jDialog = createDialog(jButton, value);
        WindowClosed.runs(jDialog, () -> jDialog = null);
        Point point = jButton.getLocationOnScreen();
        jDialog.setLocation(point);
        jDialog.setVisible(true);
      }
    });
    jPanel.add(BorderLayout.CENTER, getJTextField());
    jPanel.add(BorderLayout.EAST, jButton);
  }

  @Override // from FieldPanel
  public final JComponent getJComponent() {
    return jPanel;
  }

  /** @param component
   * @param value
   * @return dialog with parent window of given */
  protected abstract JDialog createDialog(Component component, Object value);

  /** @return button component that opens a new dialog */
  protected final Component jButton() {
    return jButton;
  }

  /** dispose open dialog */
  protected final void dispose() {
    jDialog.dispose();
  }

  /** @param value that was selected by user from within dialog */
  protected final void updateAndNotify(Object value) {
    updateJComponent(value);
    notifyListeners(getJTextField().getText());
  }
}
