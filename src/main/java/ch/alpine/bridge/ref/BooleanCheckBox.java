// code by jph, gjoel, botteonr
package ch.alpine.bridge.ref;

import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

/* package */ class BooleanCheckBox extends FieldPanel {
  /** the insets of the checkbox are typically 4,4,4,4 or 2,2,2,2 */
  private final JCheckBox jCheckBox = new JCheckBox();
  private final JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

  public BooleanCheckBox(FieldWrap fieldWrap, Boolean value) {
    super(fieldWrap);
    // ---
    FieldsEditorParam.GLOBAL.checkBoxParam.layout(jCheckBox);
    // ---
    jCheckBox.setOpaque(false);
    if (Objects.nonNull(value))
      jCheckBox.setSelected(value);
    jCheckBox.addActionListener(_ -> notifyListeners(fieldWrap.toString(jCheckBox.isSelected())));
    jPanel.add(jCheckBox);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    jCheckBox.setSelected((Boolean) value);
  }
}
