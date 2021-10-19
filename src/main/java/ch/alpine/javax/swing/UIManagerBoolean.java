// code auto generated, concept by gjoel
package ch.alpine.javax.swing;

import java.util.function.BooleanSupplier;

import javax.swing.UIManager;

public enum UIManagerBoolean implements BooleanSupplier {
  Button_defaultButtonFollowsFocus, //
  Button_rollover, //
  CheckBoxMenuItem_borderPainted, //
  CheckBox_rollover, //
  ComboBox_isEnterSelectablePopup, //
  ComboBox_noActionOnKeyNavigation, //
  FileChooser_readOnly, //
  FileChooser_useSystemExtensionHiding, //
  FileChooser_usesSingleFilePane, //
  InternalFrameTitlePane_closeButtonOpacity, //
  InternalFrameTitlePane_iconifyButtonOpacity, //
  InternalFrameTitlePane_maximizeButtonOpacity, //
  MenuItem_borderPainted, //
  Menu_borderPainted, //
  Menu_crossMenuMnemonic, //
  Menu_opaque, //
  Menu_preserveTopLevelSelection, //
  PopupMenu_consumeEventOnClose, //
  RadioButtonMenuItem_borderPainted, //
  RadioButton_rollover, //
  ScrollBar_allowsAbsolutePositioning, //
  Slider_onlyLeftMouseButtonDrag, //
  Spinner_editorBorderPainted, //
  SplitPane_centerOneTouchButtons, //
  SplitPane_oneTouchButtonsOpaque, //
  TabbedPane_contentOpaque, //
  TabbedPane_selectionFollowsFocus, //
  TabbedPane_tabsOpaque, //
  TabbedPane_tabsOverlapBorder, //
  ToolBar_isRollover, //
  ToolTip_hideAccelerator, //
  Tree_changeSelectionWithFocus, //
  Tree_drawsFocusBorderAroundIcon, //
  Tree_lineTypeDashed, //
  Tree_paintLines, //
  Tree_scrollsOnExpand, //
  ;

  @Override
  public boolean getAsBoolean() {
    return UIManager.getBoolean(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
