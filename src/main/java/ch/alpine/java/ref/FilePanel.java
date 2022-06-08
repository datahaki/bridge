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
import javax.swing.filechooser.FileFilter;

/* package */ class FilePanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  /** @param fieldWrap
   * @param _file initially
   * @param filters */
  public FilePanel(FieldWrap fieldWrap, File _file, FileFilter... filters) {
    super(fieldWrap, _file);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser();
        for (FileFilter filter : filters)
          jFileChooser.setFileFilter(filter);
        File file = new File(jTextField.getText());
        jFileChooser.setApproveButtonText("Done");
        jFileChooser.setApproveButtonToolTipText("Select file");
        jFileChooser.setDialogTitle("File selection");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // always both?
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
    jPanel.add(BorderLayout.CENTER, jTextField);
    jPanel.add(BorderLayout.EAST, jButton);
    return jPanel;
  }
}
