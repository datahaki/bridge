// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.WindowClosed;

/* package */ class LocalTimePanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(StaticHelper.BUTTON_TEXT);
  private JDialog jDialog = null;

  public LocalTimePanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap, value);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (Objects.isNull(jDialog)) {
          // fallback color is restored when "Cancel" is pressed
          jDialog = new LocalTimeDialog(jButton, lt -> {
            getJTextField().setText(lt.toString());
          });
          jDialog.setBounds(100, 100, 300, 300);
          jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          WindowClosed.runs(jDialog, () -> jDialog = null);
          jDialog.setVisible(true);
        }
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
