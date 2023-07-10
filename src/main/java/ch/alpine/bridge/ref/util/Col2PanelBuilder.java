// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;

final class Col2PanelBuilder implements PanelBuilder {
  private final JPanel jPanel = new JPanel(new GridBagLayout());
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();
  private int level = 0;

  public Col2PanelBuilder() {
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    jPanel.setOpaque(false);
  }

  @Override
  public void append(JComponent jComponent) {
    ++gridBagConstraints.gridy; // initially -1
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 0;
    jPanel.add(jComponent, gridBagConstraints);
  }

  /** @param jComponent that spreads over the entire width */
  @Override
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = createJLabel(key, field, index);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    append(jLabel);
    ++level;
  }

  /** append a row consisting of two components of equal height:
   * jComponent1 is shown to the left, and jComponent2 to the right
   * 
   * @param jComponent1
   * @param jComponent2 */
  @Override
  public void item(String key, Field field, JComponent jComponent2) {
    ++gridBagConstraints.gridy; // initially -1
    // ---
    gridBagConstraints.gridwidth = 1;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 0;
    JLabel jLabel = createJLabel(key, field, null);
    jLabel.setToolTipText(FieldToolTip.of(field));
    jPanel.add(jLabel, gridBagConstraints);
    // ---
    gridBagConstraints.gridx = 1;
    gridBagConstraints.weightx = 1;
    jPanel.add(jComponent2, gridBagConstraints);
  }

  private JLabel createJLabel(String key, Field field, Integer index) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    if (0 < level)
      jLabel.setBorder(new EmptyBorder(0, FieldsEditorParam.GLOBAL.insetLeft * level, 0, 0));
    return jLabel;
  }

  @Override
  public void pop() {
    --level;
  }

  @Override
  public JPanel getJComponent() {
    return jPanel;
  }
}
