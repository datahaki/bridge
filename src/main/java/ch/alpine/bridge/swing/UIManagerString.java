// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.util.function.Supplier;

import javax.swing.UIManager;

public enum UIManagerString implements Supplier<String> {
  ButtonUI,
  Button_rolloverIconType,
  CheckBoxMenuItemUI,
  CheckBoxMenuItem_commandSound,
  CheckBoxUI,
  ColorChooserUI,
  ComboBoxUI,
  DesktopIconUI,
  DesktopPaneUI,
  EditorPaneUI,
  FileChooserUI,
  FormattedTextFieldUI,
  InternalFrameUI,
  InternalFrame_closeSound,
  InternalFrame_maximizeSound,
  InternalFrame_minimizeSound,
  InternalFrame_restoreDownSound,
  InternalFrame_restoreUpSound,
  LabelUI,
  ListUI,
  MenuBarUI,
  MenuItemUI,
  MenuItem_acceleratorDelimiter,
  MenuItem_commandSound,
  MenuUI,
  Menu_cancelMode,
  OptionPaneUI,
  OptionPane_errorSound,
  OptionPane_informationSound,
  OptionPane_questionSound,
  OptionPane_warningSound,
  PanelUI,
  PasswordFieldUI,
  PopupMenuSeparatorUI,
  PopupMenuUI,
  PopupMenu_popupSound,
  ProgressBarUI,
  RadioButtonMenuItemUI,
  RadioButtonMenuItem_commandSound,
  RadioButtonUI,
  RootPaneUI,
  ScrollBarUI,
  ScrollPaneUI,
  SeparatorUI,
  SliderUI,
  SpinnerUI,
  SplitPaneUI,
  TabbedPaneUI,
  TableHeaderUI,
  TableUI,
  TextAreaUI,
  TextFieldUI,
  TextPaneUI,
  ToggleButtonUI,
  ToolBarSeparatorUI,
  ToolBarUI,
  ToolTipManager_enableToolTipMode,
  ToolTipUI,
  TreeUI,
  ViewportUI;

  @Override
  public String get() {
    return UIManager.getString(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
