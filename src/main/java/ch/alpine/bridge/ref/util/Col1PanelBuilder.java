// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;

final class Col1PanelBuilder implements PanelBuilder {
  private final JPanel jPanel = new JPanel(new GridBagLayout());
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

  public Col1PanelBuilder() {
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridwidth = 1; // every row consists of 1 component
    gridBagConstraints.weightx = 1; // every row may stretch to the max
    gridBagConstraints.gridx = 0; // x position of component in grid is always 0
    jPanel.setOpaque(false);
  }

  @Override
  public void append(JComponent jComponent) {
    ++gridBagConstraints.gridy; // initially -1
    jPanel.add(jComponent, gridBagConstraints);
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
    return jPanel;
  }
}
