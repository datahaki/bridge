// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

public class ShowDialog extends JDialog {
  private final ShowComponent showComponent = new ShowComponent();

  public ShowDialog(Component parentComponent, Show show) {
    super(JOptionPane.getFrameForComponent(parentComponent));
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      jToolBar.setFloatable(false);
      jToolBar.add(new JButton("save"));
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, showComponent);
    showComponent.setShow(show);
    setContentPane(jPanel);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 1000, 900);
  }
}
