// code by jph
package ch.alpine.bridge.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public final class RowPanelBuilder {
  private final GridBagLayout gridBagLayout = new GridBagLayout();
  private final JPanel jPanel = new JPanel(gridBagLayout);
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

  public RowPanelBuilder() {
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    jPanel.setOpaque(false);
  }

  /** @param jComponent that spreads over the entire width */
  public void appendRow(JComponent jComponent) {
    ++gridBagConstraints.gridy; // initially -1
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 0;
    gridBagLayout.setConstraints(jComponent, gridBagConstraints);
    jPanel.add(jComponent);
  }

  /** append a row consisting of two components of equal height:
   * jComponent1 is shown to the left, and jComponent2 to the right
   * 
   * @param jComponent1
   * @param jComponent2 */
  public void appendRow(JComponent jComponent1, JComponent jComponent2) {
    ++gridBagConstraints.gridy; // initially -1
    // ---
    gridBagConstraints.gridwidth = 1;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 0;
    gridBagLayout.setConstraints(jComponent1, gridBagConstraints);
    jPanel.add(jComponent1);
    // ---
    gridBagConstraints.gridx = 1;
    gridBagConstraints.weightx = 1;
    gridBagLayout.setConstraints(jComponent2, gridBagConstraints);
    jPanel.add(jComponent2);
  }

  public JPanel getJPanel() {
    return jPanel;
  }
}
