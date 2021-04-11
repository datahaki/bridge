// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JTextField;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

/* package */ class StringPanel extends FieldPanel {
  private static final Color LABEL = new Color(51, 51, 51);
  // ---
  protected final JTextField jTextField;

  public StringPanel(FieldWrap fieldType, Object value) {
    super(fieldType);
    jTextField = new JTextField(fieldType.toString(value));
    jTextField.setFont(FieldPanel.FONT);
    jTextField.setForeground(LABEL);
    jTextField.addActionListener(l -> nofifyIfValid(jTextField.getText()));
    jTextField.addKeyListener(new KeyAdapter() {
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
    return fieldType().isValidValue(fieldType().toValue(string));
  }

  private void indicateGui() {
    boolean isOk = isValid(jTextField.getText());
    jTextField.setBackground(isOk //
        ? Color.WHITE
        : FAIL);
  }
}
