// code by jph
package ch.alpine.bridge.awt;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public final class ColumnPanel extends JPanel {
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

  public ColumnPanel() {
    super(new GridBagLayout());
    gridBagConstraints.fill = GridBagConstraints.BOTH; // GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridwidth = 1; // every row consists of 1 component
    gridBagConstraints.weightx = 1; // every row may stretch to the max
    gridBagConstraints.weighty = 1; // every row may stretch to the max
    gridBagConstraints.gridx = 0; // x position of component in grid is always 0
    setOpaque(false);
  }

  @Override
  public Component add(Component component) {
    ++gridBagConstraints.gridy; // initially -1
    add(component, gridBagConstraints);
    return component;
  }
}
