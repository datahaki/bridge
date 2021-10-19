// code auto generated, concept by gjoel
package ch.alpine.javax.swing;

import java.awt.Font;
import java.util.function.Supplier;

import javax.swing.UIManager;

public enum UIManagerFont implements Supplier<Font> {
  Button_font, //
  CheckBoxMenuItem_acceleratorFont, //
  CheckBoxMenuItem_font, //
  CheckBox_font, //
  ColorChooser_font, //
  ComboBox_font, //
  DesktopIcon_font, //
  EditorPane_font, //
  FormattedTextField_font, //
  InternalFrame_titleFont, //
  Label_font, //
  List_font, //
  MenuBar_font, //
  MenuItem_acceleratorFont, //
  MenuItem_font, //
  Menu_acceleratorFont, //
  Menu_font, //
  OptionPane_font, //
  Panel_font, //
  PasswordField_font, //
  PopupMenu_font, //
  ProgressBar_font, //
  RadioButtonMenuItem_acceleratorFont, //
  RadioButtonMenuItem_font, //
  RadioButton_font, //
  ScrollPane_font, //
  Slider_font, //
  Spinner_font, //
  TabbedPane_font, //
  TableHeader_font, //
  Table_font, //
  TextArea_font, //
  TextField_font, //
  TextPane_font, //
  TitledBorder_font, //
  ToggleButton_font, //
  ToolBar_font, //
  ToolTip_font, //
  Tree_font, //
  Viewport_font, //
  ;

  @Override
  public Font get() {
    return UIManager.getFont(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
