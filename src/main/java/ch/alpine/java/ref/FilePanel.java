// code by jph
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/* package */ class FilePanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  /** @param fieldWrap
   * @param _file initially */
  public FilePanel(FieldWrap fieldWrap, File _file) {
    super(fieldWrap, _file);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser();
        File file = new File(jTextField.getText());
        jFileChooser.setApproveButtonText("Done");
        jFileChooser.setApproveButtonToolTipText("Select file");
        jFileChooser.setDialogTitle("File selection");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.setSelectedFile(file);
        int openDialog = jFileChooser.showOpenDialog(jButton);
        if (openDialog == JFileChooser.APPROVE_OPTION) {
          File selectedFile = jFileChooser.getSelectedFile();
          String string = fieldWrap.toString(selectedFile);
          jTextField.setText(string);
          indicateGui();
          nofifyIfValid(string);
        }
      }
    });
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", jTextField);
    jPanel.add("East", jButton);
    return jPanel;
  }
}
