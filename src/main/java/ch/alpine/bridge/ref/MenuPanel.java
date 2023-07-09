// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ch.alpine.bridge.swing.SpinnerMenu;

/* package */ class MenuPanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(StaticHelper.BUTTON_TEXT);

  /** @param fieldWrap
   * @param value during initialization
   * @param supplier invoked when menu button "?" is pressed */
  public MenuPanel(FieldWrap fieldWrap, Object value, Supplier<List<Object>> supplier) {
    super(fieldWrap, value);
    jButton.addActionListener(actionEvent -> {
      JComponent jTextField = getTextFieldComponent();
      Font font = jTextField.getFont();
      SpinnerMenu<Object> spinnerMenu = new SpinnerMenu<>( //
          supplier.get(), // options
          fieldWrap().toValue(getText()), // selected value
          fieldWrap::toString, // object to string function
          font, //
          false); // no hover
      spinnerMenu.addSpinnerListener(value1 -> {
        String string = fieldWrap.toString(value1);
        setText(string);
        indicateGui();
        nofifyIfValid(string);
      });
      spinnerMenu.showRight(jButton);
    });
    jPanel.add(getTextFieldComponent(), BorderLayout.CENTER);
    jPanel.add(jButton, BorderLayout.EAST);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }
}
