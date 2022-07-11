// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.UIManager;

import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.swing.CheckBoxIcon;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

/** FieldsEditorManager manages additional keys to specify the layout of instances of
 * {@link FieldsEditor} that are not already covered by {@link UIManager}. */
@ReflectionMarker
public class FieldsEditorParam {
  public static final FieldsEditorParam GLOBAL = new FieldsEditorParam();
  // ---
  public Boolean toolbarHeight_override = false;
  @FieldSelectionArray({ "28", "30", "32" })
  public Scalar toolbarHeight = RealScalar.of(28);
  // ---
  public Boolean textFieldFont_override = false;
  public Font textFieldFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 15);
  // ---
  public Boolean checkBoxIcon_override = false;
  public CheckBoxIcon checkBoxIcon = CheckBoxIcon.METRO;
  @FieldSelectionArray({ "16", "20", "24", "28", "32" })
  public Scalar checkBoxIconSize = RealScalar.of(16);

  public void maxHeight(JComponent jComponent) {
    if (toolbarHeight_override) {
      int height = toolbarHeight.number().intValue();
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = Math.max(dimension.height, height);
      jComponent.setPreferredSize(dimension);
    }
  }

  public void setFont(Component component) {
    if (textFieldFont_override)
      component.setFont(textFieldFont);
  }

  public void setIcon(JCheckBox jCheckBox) {
    if (checkBoxIcon_override) {
      int n = checkBoxIconSize.number().intValue();
      jCheckBox.setIcon(checkBoxIcon.create(n, false));
      jCheckBox.setSelectedIcon(checkBoxIcon.create(n, true));
    }
  }
}
