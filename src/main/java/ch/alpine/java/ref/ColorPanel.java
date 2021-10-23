// code by jph
package ch.alpine.java.ref;

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
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.img.ColorFormat;

/* package */ class ColorPanel extends StringPanel {
  private final JButton jButton = new JButton("?");
  private JDialog jDialog = null;

  public ColorPanel(FieldWrap fieldWrap, Object object) {
    super(fieldWrap, object);
    jButton.setBackground(getColor());
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (Objects.isNull(jDialog)) {
          Color fallback = getColor();
          JColorChooser jColorChooser = new JColorChooser(fallback);
          ColorSelectionModel colorSelectionModel = jColorChooser.getSelectionModel();
          ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
              updateJComponent(jColorChooser.getColor());
              notifyListeners(jTextField.getText());
            }
          };
          colorSelectionModel.addChangeListener(changeListener);
          jDialog = JColorChooser.createDialog( //
              jColorChooser, "color selection", false, jColorChooser, i -> jDialog.dispose(), //
              i -> {
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
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jTextField);
    jPanel.add(BorderLayout.EAST, jButton);
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    Color color = (Color) value;
    String string = fieldWrap().toString(color);
    jTextField.setText(string);
    jButton.setBackground(color);
  }
}
