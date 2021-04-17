// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JTextField;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

// TODO enable Ctrl+z etc. for undo and redo
/* package */ class StringPanel extends FieldPanel {
  private static final Color LABEL = new Color(51, 51, 51);
  // ---
  protected final JTextField jTextField;
  private String fallbackValue = null;

  public StringPanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap);
    jTextField = Objects.isNull(value) //
        ? new JTextField()
        : new JTextField(fallbackValue = fieldWrap.toString(value));
    jTextField.setFont(FieldPanel.FONT);
    jTextField.setForeground(LABEL);
    jTextField.addActionListener(l -> nofifyIfValid(jTextField.getText()));
    jTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
        case KeyEvent.VK_ESCAPE: {
          if (Objects.nonNull(fallbackValue))
            jTextField.setText(fallbackValue);
          break;
        }
        default:
          break;
        }
      }

      @Override
      public void keyReleased(KeyEvent keyEvent) {
        indicateGui();
      }
    });
    jTextField.addFocusListener(new FocusListener() {
      private String value;

      @Override
      public void focusGained(FocusEvent focusEvent) {
        value = jTextField.getText();
      }

      @Override
      public void focusLost(FocusEvent focusEvent) {
        String string = jTextField.getText();
        if (!string.equals(value))
          nofifyIfValid(value = string);
      }
    });
    indicateGui();
    addListener(string -> fallbackValue = string);
  }

  @Override
  public JComponent getJComponent() {
    return jTextField;
  }

  private void nofifyIfValid(String string) {
    if (isValid(string))
      notifyListeners(string);
  }

  private boolean isValid(String string) {
    Object object = fieldWrap().toValue(string);
    return Objects.nonNull(object) //
        && fieldWrap().isValidValue(object);
  }

  protected void indicateGui() {
    boolean isOk = isValid(jTextField.getText());
    jTextField.setBackground(isOk //
        ? Color.WHITE
        : FAIL);
  }
}
