// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/* package */ class BooleanCheckBox extends FieldPanel {
  /** the insets of the checkbox are typically 4,4,4,4 or 2,2,2,2 */
  private final JCheckBox jCheckBox = new JCheckBox();

  public BooleanCheckBox(FieldWrap fieldWrap, Boolean value) {
    super(fieldWrap);
    // ---
    FieldsEditorParam.GLOBAL.setIcon(jCheckBox);
    // System.out.println(jCheckBox.getInsets());
    // ---
    jCheckBox.setOpaque(false);
    if (Objects.nonNull(value))
      jCheckBox.setSelected(value);
    jCheckBox.addActionListener(event -> notifyListeners(fieldWrap.toString(jCheckBox.isSelected())));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jCheckBox;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    jCheckBox.setSelected((Boolean) value);
  }
}
