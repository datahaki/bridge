// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.UIManager;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.swing.CheckBoxIcon;
import ch.alpine.bridge.swing.FontParam;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

/** FieldsEditorManager manages additional keys to specify the layout of instances of
 * {@link FieldsEditor} that are not already covered by {@link UIManager}. */
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

  public static void setHeight(Scalar fieldsEditorKey, JComponent jComponent) {
    if (Objects.nonNull(fieldsEditorKey)) {
      int height = fieldsEditorKey.number().intValue();
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = height;
      jComponent.setPreferredSize(dimension);
    }
  }

  public void maxHeight(JComponent jComponent) {
    if (Objects.nonNull(toolbarComponentHeight)) {
      int height = toolbarComponentHeight.number().intValue();
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = Math.max(dimension.height, height);
      jComponent.setPreferredSize(dimension);
    }
  }

  public void setFont(Component component) {
    if (Objects.nonNull(textFieldFont))
      component.setFont(textFieldFont.toFont());
  }

  public void setIcon(JCheckBox jCheckBox) {
    int n = checkBoxIconSize.number().intValue();
    jCheckBox.setIcon(checkBoxIcon.create(n, false));
    jCheckBox.setSelectedIcon(checkBoxIcon.create(n, true));
  }
}
