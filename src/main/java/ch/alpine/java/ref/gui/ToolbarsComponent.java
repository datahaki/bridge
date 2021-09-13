// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class ToolbarsComponent {
  public static final int WEST_WIDTH = 140;
  public static final int HEIGHT = 30;
  public static final int HEIGHT_CBOX = 15;
  public static final String UNKNOWN = "<unknown>";

  /** @param align
   * @return */
  public static JToolBar createJToolBar(int align) {
    JToolBar jToolBar = new JToolBar();
    jToolBar.setFloatable(false);
    jToolBar.setLayout(new FlowLayout(align, 3, 0));
    return jToolBar;
  }

  // ==================================================
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final RowPanel rowTitle = new RowPanel();
  private final RowPanel rowActor = new RowPanel();

  public ToolbarsComponent() {
    jPanel.add(rowTitle.jPanel, BorderLayout.WEST);
    jPanel.add(rowActor.jPanel, BorderLayout.CENTER);
  }

  public void addSeparator() {
    JLabel jLabelW = new JLabel();
    jLabelW.setBackground(Color.GRAY);
    jLabelW.setOpaque(true);
    JLabel jLabelC = new JLabel();
    jLabelC.setBackground(Color.GRAY);
    jLabelC.setOpaque(true);
    addPair(jLabelW, jLabelC, 5);
  }

  public JToolBar createRow(String title) {
    return createRow(title, HEIGHT);
  }

  public JToolBar createRow(String title, int height) {
    JToolBar jToolBar1 = createJToolBar(FlowLayout.RIGHT);
    jToolBar1.add(new JLabel(title));
    JToolBar jToolBar2 = createJToolBar(FlowLayout.LEFT);
    addPair(jToolBar1, jToolBar2, height);
    return jToolBar2;
  }

  private void addPair(JComponent west, JComponent center) {
    addPair(west, center, HEIGHT);
  }

  public void addPair(JComponent west, JComponent center, int height) {
    {
      Dimension dimension = west.getPreferredSize();
      dimension.height = height;
      west.setPreferredSize(dimension);
    }
    rowTitle.add(west);
    {
      Dimension dimension = center.getPreferredSize();
      dimension.height = height;
      center.setPreferredSize(dimension);
    }
    rowActor.add(center);
  }

  // ==================================================
  /** @param title
   * @return editable text field that allows user modification */
  public JTextField createEditing(String title) {
    JTextField jTextField = new JTextField(20);
    jTextField.setText(UNKNOWN);
    JToolBar jToolBar1 = createJToolBar(FlowLayout.RIGHT);
    JLabel jLabel = new JLabel(title);
    jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, HEIGHT));
    jToolBar1.add(jLabel);
    addPair(jToolBar1, jTextField);
    return jTextField;
  }

  // ==================================================
  /** @param title
   * @return non-editable text field to display values */
  public JTextField createReading(String title) {
    JTextField jTextField = createEditing(title);
    jTextField.setEditable(false);
    jTextField.setEnabled(false);
    jTextField.setDisabledTextColor(Color.BLACK);
    return jTextField;
  }

  public JCheckBox createReadingCheckbox(String title) {
    JCheckBox jCheckBox = new JCheckBox(title);
    jCheckBox.setEnabled(false);
    JLabel jLabel = new JLabel(" ");
    jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, HEIGHT_CBOX));
    addPair(jLabel, jCheckBox, HEIGHT_CBOX);
    return jCheckBox;
  }

  // ==================================================
  /** @return component that contains the labeled input fields only, but not
   * the text summary below */
  public JScrollPane createJScrollPane() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(this.jPanel, BorderLayout.NORTH);
    return new JScrollPane(jPanel);
  }

  public JPanel getJPanel() {
    return jPanel;
  }
}
