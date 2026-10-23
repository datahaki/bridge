package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;

class ToolbarBuilder implements PanelBuilder {
  private final JToolBar jToolBar = new JToolBar();

  @Override
  public void append(JComponent jComponent) {
    jToolBar.add(jComponent);
  }

  @Override
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    append(jLabel);
  }

  @Override
  public void item(String key, Field field, JComponent jComponent) {
    // TODO BRIDGE design no good
    if (!field.getType().equals(Boolean.class)) {
      JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, null));
      jLabel.setToolTipText(FieldToolTip.of(field));
      append(jLabel);
    }
    append(jComponent);
  }

  @Override
  public void pop() {
    // ---
  }

  @Override
  public JComponent getJComponent() {
    return jToolBar;
  }
}
