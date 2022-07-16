// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.util.function.Supplier;

import javax.swing.UIManager;
import javax.swing.border.Border;

public enum UIManagerBorder implements Supplier<Border> {
  Button_border,
  CheckBoxMenuItem_border,
  CheckBox_border,
  DesktopIcon_border,
  EditorPane_border,
  FormattedTextField_border,
  InternalFrame_border,
  InternalFrame_optionDialogBorder,
  InternalFrame_paletteBorder,
  List_focusCellHighlightBorder,
  List_noFocusBorder,
  MenuBar_border,
  MenuItem_border,
  Menu_border,
  OptionPane_border,
  OptionPane_buttonAreaBorder,
  OptionPane_messageAreaBorder,
  PasswordField_border,
  PopupMenu_border,
  ProgressBar_border,
  RadioButtonMenuItem_border,
  RadioButton_border,
  RootPane_colorChooserDialogBorder,
  RootPane_errorDialogBorder,
  RootPane_fileChooserDialogBorder,
  RootPane_frameBorder,
  RootPane_informationDialogBorder,
  RootPane_plainDialogBorder,
  RootPane_questionDialogBorder,
  RootPane_warningDialogBorder,
  ScrollPane_border,
  Spinner_arrowButtonBorder,
  Spinner_border,
  SplitPaneDivider_border,
  SplitPane_border,
  TableHeader_cellBorder,
  Table_focusCellHighlightBorder,
  Table_scrollPaneBorder,
  TextArea_border,
  TextField_border,
  TextPane_border,
  TitledBorder_border,
  ToggleButton_border,
  ToolBar_border,
  ToolBar_nonrolloverBorder,
  ToolBar_rolloverBorder,
  ToolTip_border,
  ToolTip_borderInactive,
  Tree_editorBorder;

  @Override
  public Border get() {
    return UIManager.getBorder(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
