// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.swing.CheckBoxIcon;

/** FieldsEditorManager manages additional keys to specify the layout of instances of
 * {@link FieldsEditor} that are not already covered by {@link UIManager}. */
@ReflectionMarker
public class FieldsEditorParam {
  public static final FieldsEditorParam GLOBAL = new FieldsEditorParam();
  // ---
  /** min height applicable to all {@link FieldPanel}s */
  public Boolean componentMinExtension_override = false;
  @FieldSelectionArray({ "28", "30", "32" })
  public Integer componentMinExtension = 28;
  // ---
  /** font applicable to {@link StringPanel} and {@link EnumPanel} */
  public Boolean labelFont_override = false;
  public Font labelFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 15);
  // ---
  /** font applicable to {@link StringPanel} and {@link EnumPanel} */
  public Boolean textFieldFont_override = false;
  public Font textFieldFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 15);
  // ---
  /** icon applicable to {@link BooleanCheckBox} */
  public Boolean checkBoxIcon_override = false;
  public CheckBoxIcon checkBoxIcon = CheckBoxIcon.METRO;
  @FieldSelectionArray({ "16", "20", "24", "28", "32" })
  public Integer checkBoxIconSize = 16;
  public Color stringPanel_Fail_BGND = new Color(255, 192, 192);
  public Color stringPanel_Fail_TEXT = new Color(51, 51, 51);

  public void minExtension(JComponent jComponent) {
    if (componentMinExtension_override) {
      Component[] components = jComponent.getComponents();
      if (0 < components.length)
        for (Component component : components)
          minExtension((JComponent) component);
      else //
      if (jComponent instanceof AbstractButton || //
          jComponent instanceof JTextComponent || //
          jComponent instanceof JSlider) {
        int lowerBound = componentMinExtension;
        Dimension dimension = jComponent.getPreferredSize();
        dimension.width = Math.max(dimension.width, lowerBound);
        dimension.height = Math.max(dimension.height, lowerBound);
        jComponent.setPreferredSize(dimension);
      }
    }
  }

  public void setFont(JTextField jTextField) {
    if (textFieldFont_override)
      jTextField.setFont(textFieldFont);
  }

  public void setIcon(JCheckBox jCheckBox) {
    if (checkBoxIcon_override) {
      int n = checkBoxIconSize;
      jCheckBox.setIcon(checkBoxIcon.create(n, false));
      jCheckBox.setSelectedIcon(checkBoxIcon.create(n, true));
    }
  }

  public JLabel createLabel(String text) {
    JLabel jLabel = new JLabel(text);
    if (labelFont_override)
      jLabel.setFont(labelFont);
    return jLabel;
  }
}
