// code auto generated, concept by gjoel
package ch.alpine.javax.swing;

import java.awt.Insets;
import java.util.function.Supplier;

import javax.swing.UIManager;

public enum UIManagerInsets implements Supplier<Insets> {
  Button_margin, //
  CheckBoxMenuItem_margin, //
  CheckBox_margin, //
  CheckBox_totalInsets, //
  Desktop_minOnScreenInsets, //
  EditorPane_margin, //
  FormattedTextField_margin, //
  MenuItem_margin, //
  Menu_margin, //
  PasswordField_margin, //
  RadioButtonMenuItem_margin, //
  RadioButton_margin, //
  RadioButton_totalInsets, //
  Slider_focusInsets, //
  Spinner_arrowButtonInsets, //
  TabbedPane_contentBorderInsets, //
  TabbedPane_selectedTabPadInsets, //
  TabbedPane_tabAreaInsets, //
  TabbedPane_tabInsets, //
  TextArea_margin, //
  TextField_margin, //
  TextPane_margin, //
  ToggleButton_margin, //
  ;

  @Override
  public Insets get() {
    return UIManager.getInsets(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
