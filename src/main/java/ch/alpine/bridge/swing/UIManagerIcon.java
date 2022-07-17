// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.util.function.Supplier;

import javax.swing.Icon;
import javax.swing.UIManager;

public enum UIManagerIcon implements Supplier<Icon> {
  CheckBoxMenuItem_arrowIcon,
  CheckBoxMenuItem_checkIcon,
  CheckBox_icon,
  FileChooser_detailsViewIcon,
  FileChooser_homeFolderIcon,
  FileChooser_listViewIcon,
  FileChooser_newFolderIcon,
  FileChooser_upFolderIcon,
  FileView_computerIcon,
  FileView_directoryIcon,
  FileView_fileIcon,
  FileView_floppyDriveIcon,
  FileView_hardDriveIcon,
  InternalFrame_closeIcon,
  InternalFrame_icon,
  InternalFrame_iconifyIcon,
  InternalFrame_maximizeIcon,
  InternalFrame_minimizeIcon,
  InternalFrame_paletteCloseIcon,
  MenuItem_arrowIcon,
  Menu_arrowIcon,
  OptionPane_errorIcon,
  OptionPane_informationIcon,
  OptionPane_questionIcon,
  OptionPane_warningIcon,
  RadioButtonMenuItem_arrowIcon,
  RadioButtonMenuItem_checkIcon,
  RadioButton_icon,
  Slider_horizontalThumbIcon,
  Slider_verticalThumbIcon,
  Table_ascendingSortIcon,
  Table_descendingSortIcon,
  Tree_closedIcon,
  Tree_collapsedIcon,
  Tree_expandedIcon,
  Tree_leafIcon,
  Tree_openIcon,
  html_missingImage,
  html_pendingImage;

  @Override
  public Icon get() {
    return UIManager.getIcon(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
