// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.util.function.IntSupplier;

import javax.swing.UIManager;

public enum UIManagerInt implements IntSupplier {
  Button_textIconGap,
  Button_textShiftOffset,
  CheckBox_textIconGap,
  CheckBox_textShiftOffset,
  DesktopIcon_width,
  EditorPane_caretBlinkRate,
  FormattedTextField_caretBlinkRate,
  InternalFrame_paletteTitleHeight,
  Menu_menuPopupOffsetX,
  Menu_menuPopupOffsetY,
  Menu_submenuPopupOffsetX,
  Menu_submenuPopupOffsetY,
  OptionPane_buttonClickThreshhold,
  PasswordField_caretBlinkRate,
  ProgressBar_cellLength,
  ProgressBar_cellSpacing,
  ProgressBar_cycleTime,
  ProgressBar_repaintInterval,
  RadioButton_textIconGap,
  RadioButton_textShiftOffset,
  ScrollBar_width,
  Slider_majorTickLength,
  Slider_trackWidth,
  Spinner_editorAlignment,
  SplitPane_dividerSize,
  TabbedPane_labelShift,
  TabbedPane_selectedLabelShift,
  TabbedPane_tabRunOverlay,
  TabbedPane_textIconGap,
  TextArea_caretBlinkRate,
  TextField_caretBlinkRate,
  TextPane_caretBlinkRate,
  ToggleButton_textIconGap,
  ToggleButton_textShiftOffset,
  Tree_leftChildIndent,
  Tree_rightChildIndent,
  Tree_rowHeight;

  @Override
  public int getAsInt() {
    return UIManager.getInt(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
