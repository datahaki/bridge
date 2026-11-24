// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Dimension;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.alpine.bridge.awt.ColoredEmptyBorder;
import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;
import ch.alpine.tensor.col.ColorDataIndexed;
import ch.alpine.tensor.col.ColorDataLists;

final class Col1PanelBuilder implements PanelBuilder {
  private static final ColorDataIndexed CDI = ColorDataLists._097.cyclic();
  private final Deque<ColumnPanel> deque = new ArrayDeque<>();

  public Col1PanelBuilder() {
    deque.add(new ColumnPanel());
  }

  @Override
  public void append(JComponent jComponent) {
    throw new IllegalStateException();
  }

  @Override
  public void push(String key, Field field, Integer index) {
    ColumnPanel columnPanel = new ColumnPanel();
    columnPanel.setBorder(new ColoredEmptyBorder(3, FieldsEditorParam.GLOBAL.insetLeft, 0, 0, CDI.getColor(deque.size() - 1)));
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    columnPanel.add(jLabel);
    deque.push(columnPanel);
  }

  @Override
  public void item(String key, Field field, JComponent jComponent) {
    ColumnPanel columnPanel = deque.peek();
    // TODO design no good
    if (!field.getType().equals(Boolean.class)) {
      JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, null));
      jLabel.setToolTipText(FieldToolTip.of(field));
      columnPanel.add(jLabel);
    }
    columnPanel.add(jComponent);
    columnPanel.add(Box.createRigidArea(new Dimension(0, 3)));
  }

  @Override
  public void pop() {
    ColumnPanel columnPanel = deque.pop();
    deque.peek().add(columnPanel);
  }

  @Override
  public JPanel getJComponent() {
    return deque.peek();
  }
}
