// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;

final class Col1PanelBuilder implements PanelBuilder {
  private final ColumnPanel columnPanel = new ColumnPanel();
  int depth = 0;

  @Override
  public void append(JComponent jComponent) {
    columnPanel.add(jComponent);
  }

  @Override
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    append(FieldsEditorParam.GLOBAL.createInset(depth, jLabel));
    ++depth;
  }

  @Override
  public void item(String key, Field field, JComponent jComponent) {
    // TODO design no good
    if (!field.getType().equals(Boolean.class)) {
      JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, null));
      jLabel.setToolTipText(FieldToolTip.of(field));
      append(FieldsEditorParam.GLOBAL.createInset(depth, jLabel));
    }
    append(FieldsEditorParam.GLOBAL.createInset(depth, jComponent));
  }

  @Override
  public void pop() {
    --depth;
  }

  @Override
  public JPanel getJComponent() {
    return columnPanel;
  }
}
