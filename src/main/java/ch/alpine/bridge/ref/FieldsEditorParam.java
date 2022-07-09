// code by jph
package ch.alpine.bridge.ref;

import javax.swing.Icon;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.CheckBoxIcon;
import ch.alpine.bridge.swing.FontParam;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class FieldsEditorParam {
  public static final FieldsEditorParam GLOBAL = new FieldsEditorParam();
  // ---
  public Scalar toolbarComponentHeight;
  public Scalar stringPanelHeight;
  public Scalar sliderHeight;
  public Scalar buttonHeight;
  public Scalar labelHeight;
  public FontParam textFieldFont;
  public CheckBoxIcon checkBoxIcon = CheckBoxIcon.DEFAULT;
  public Scalar checkBoxIconSize = RealScalar.of(16);

  public Icon getIcon(boolean selected) {
    return checkBoxIcon.create(checkBoxIconSize.number().intValue(), selected);
  }
}
