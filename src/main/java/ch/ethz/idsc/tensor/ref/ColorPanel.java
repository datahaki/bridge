// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorFormat;

/* package */ class ColorPanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  public ColorPanel(FieldWrap fieldType, Object object) {
    super(fieldType, object);
    jButton.setBackground(getColor());
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        Color color = JColorChooser.showDialog(jButton, "pick color", getColor());
        if (Objects.nonNull(color)) {
          jButton.setBackground(color);
          String string = fieldType.toString(color);
          jTextField.setText(string);
          notifyListeners(string);
        }
      }
    });
    jTextField.setEditable(false);
  }

  private Color getColor() {
    try {
      return ColorFormat.toColor(Tensors.fromString(jTextField.getText()));
    } catch (Exception exception) {
      // ---
    }
    return Color.WHITE;
  }

  @Override
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", jTextField);
    jPanel.add("East", jButton);
    return jPanel;
  }
}
