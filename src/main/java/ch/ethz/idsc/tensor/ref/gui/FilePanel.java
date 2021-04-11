// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ch.ethz.idsc.tensor.ref.FieldType;

public class FilePanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  public FilePanel(FieldType fieldType, File file) {
    super(fieldType, file);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser(file);
        jFileChooser.setBounds(100, 100, 600, 600);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int openDialog = jFileChooser.showOpenDialog(null);
        if (openDialog == JFileChooser.APPROVE_OPTION)
          jTextField.setText(jFileChooser.getSelectedFile().toString());
      }
    });
  }

  @Override
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", jTextField);
    jPanel.add("East", jButton);
    return jPanel;
  }
}
