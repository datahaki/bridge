// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/* package */ class FilePanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton("?");

  /** @param fieldWrap
   * @param _file initially
   * @param fileFilters */
  public FilePanel(FieldWrap fieldWrap, File _file, List<FileFilter> fileFilters) {
    super(fieldWrap, _file);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser();
        for (FileFilter fileFilter : fileFilters)
          jFileChooser.setFileFilter(fileFilter);
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
          getJTextField().setText(string);
          indicateGui();
          nofifyIfValid(string);
        }
      }
    });
    jPanel.add(BorderLayout.CENTER, getJTextField());
    jPanel.add(BorderLayout.EAST, jButton);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }
}
