// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.awt.Dimension;
import java.util.function.Supplier;

import javax.swing.UIManager;

public enum UIManagerDimension implements Supplier<Dimension> {
  ColorChooser_swatchesRecentSwatchSize, //
  ColorChooser_swatchesSwatchSize, //
  OptionPane_minimumSize, //
  ProgressBar_horizontalSize, //
  ProgressBar_verticalSize, //
  ScrollBar_maximumThumbSize, //
  ScrollBar_minimumThumbSize, //
  Slider_horizontalSize, //
  Slider_minimumHorizontalSize, //
  Slider_minimumVerticalSize, //
  Slider_verticalSize, //
  Spinner_arrowButtonSize, //
  ToolBar_separatorSize, //
  ;

  @Override
  public Dimension get() {
    return UIManager.getDimension(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
