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

  @Override
  public void append(JComponent jComponent) {
    columnPanel.add(jComponent);
  }

  @Override
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    append(jLabel);
  }

  @Override
  public void item(String key, Field field, JComponent jComponent) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, null));
    jLabel.setToolTipText(FieldToolTip.of(field));
    append(jLabel);
    append(jComponent);
  }

  @Override
  public void pop() {
    // ---
  }

  @Override
  public JPanel getJComponent() {
    return columnPanel;
  }
}
