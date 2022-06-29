// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
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

  public LocalTimePanel(FieldWrap fieldWrap, LocalTime localTime) {
    super(fieldWrap, localTime);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (Objects.isNull(jDialog)) {
          // fallback localTime is restored when "Cancel" is pressed
          jDialog = new LocalTimeDialog(jButton, localTime, localTime -> getJTextField().setText(localTime.toString()));
          Point point = jButton.getLocationOnScreen();
          jDialog.setLocation(point);
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
