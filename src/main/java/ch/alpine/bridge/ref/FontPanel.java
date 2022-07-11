// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.swing.FontDialog;

/* package */ class FontPanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(StaticHelper.BUTTON_TEXT);
  private JDialog jDialog = null;

  public FontPanel(FieldWrap fieldWrap, Font font) {
    super(fieldWrap, font);
    getJTextField().setEditable(false);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (Objects.isNull(jDialog)) {
          // fallback localTime is restored when "Cancel" is pressed
          jDialog = new FontDialog(jButton, font, font -> getJTextField().setText(FontParser.toString(font)));
          WindowClosed.runs(jDialog, () -> jDialog = null);
          Point point = jButton.getLocationOnScreen();
          jDialog.setLocation(point);
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
