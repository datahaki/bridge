// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.img.ColorFormat;

/* package */ class ColorPanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton("?");
  /** For each instance of {@link ColorPanel}, a single JColorChooser may be opened
   * by the user. The jDialog field is non-null whenever the dialog is visible. */
  private JDialog jDialog = null;

  public ColorPanel(FieldWrap fieldWrap, Object object) {
    super(fieldWrap, object);
    jButton.setBackground(getColor());
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (Objects.isNull(jDialog)) {
          // fallback color is restored when "Cancel" is pressed
          Color fallback = getColor();
          JColorChooser jColorChooser = new JColorChooser(fallback);
          jColorChooser.getSelectionModel().addChangeListener(changeEvent -> {
            updateJComponent(jColorChooser.getColor());
            notifyListeners(jTextField.getText());
          });
          jDialog = JColorChooser.createDialog( //
              jButton, "color selection: " + fieldWrap.getField().getName(), //
              false, jColorChooser, //
              i -> jDialog.dispose(), // ok listener
              i -> { // cancel listener
                jDialog.dispose();
                updateJComponent(fallback);
                notifyListeners(jTextField.getText());
              });
          jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
              jDialog = null;
            }
          });
          jDialog.setVisible(true);
        }
      }
    });
    jTextField.setEditable(false);
    jPanel.add(BorderLayout.CENTER, jTextField);
    jPanel.add(BorderLayout.EAST, jButton);
  }

  private Color getColor() {
    try {
      return ColorFormat.toColor(Tensors.fromString(jTextField.getText()));
    } catch (Exception exception) {
      // ---
    }
    return Color.WHITE;
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    super.updateJComponent(value);
    Color color = (Color) value;
    jButton.setBackground(color);
  }
}
