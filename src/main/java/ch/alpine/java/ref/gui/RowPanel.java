// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/* package */ final class RowPanel {
  private final GridBagLayout gridBagLayout = new GridBagLayout();
  public final JPanel jPanel = new JPanel(gridBagLayout);
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

  public RowPanel() {
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    jPanel.setOpaque(false);
  }

  public void appendRow(JComponent jComponent, int height) {
    setHeight(jComponent, height);
    ++gridBagConstraints.gridy; // initially -1
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 0;
    gridBagLayout.setConstraints(jComponent, gridBagConstraints);
    jPanel.add(jComponent);
  }

  public void appendRow(JComponent jComponent1, JComponent jComponent2, int height) {
    setHeight(jComponent1, height);
    setHeight(jComponent2, height);
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

  public JScrollPane createJScrollPane() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(this.jPanel, BorderLayout.NORTH);
    return new JScrollPane(jPanel);
  }

  private static void setHeight(JComponent jComponent, int height) {
    Dimension dimension = jComponent.getPreferredSize();
    dimension.height = height;
    jComponent.setPreferredSize(dimension);
  }
}
