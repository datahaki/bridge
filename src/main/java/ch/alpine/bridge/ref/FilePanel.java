// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/* package */ class FilePanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton(StaticHelper.BUTTON_TEXT);

  /** @param fieldWrap
   * @param _file initially
   * @param fileFilters */
  public FilePanel(FieldWrap fieldWrap, File _file, List<FileFilter> fileFilters) {
    super(fieldWrap, _file);
    jButton.addActionListener(actionEvent -> {
      JFileChooser jFileChooser = new JFileChooser();
      for (FileFilter fileFilter : fileFilters)
        jFileChooser.setFileFilter(fileFilter);
      File file = new File(getText());
      jFileChooser.setApproveButtonText("Done");
      jFileChooser.setApproveButtonToolTipText("Select file");
      jFileChooser.setDialogTitle("File selection");
      jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // always both?
      jFileChooser.setSelectedFile(file);
      int openDialog = jFileChooser.showOpenDialog(jButton);
      if (openDialog == JFileChooser.APPROVE_OPTION) {
        File selectedFile = jFileChooser.getSelectedFile();
        String string = fieldWrap.toString(selectedFile);
        setText(string);
        indicateGui();
        nofifyIfValid(string);
      }
    });
    jPanel.add(getTextFieldComponent(), BorderLayout.CENTER);
    jPanel.add(jButton, BorderLayout.EAST);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jPanel;
  }
}
