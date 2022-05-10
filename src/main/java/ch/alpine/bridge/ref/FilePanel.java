// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/* package */ class FilePanel extends StringPanel {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JButton jButton = new JButton("?");

  /** @param fieldWrap
   * @param _file initially */
  public FilePanel(FieldWrap fieldWrap, File _file) {
    super(fieldWrap, _file);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser();
        File file = new File(getJTextField().getText());
        jFileChooser.setApproveButtonText("Done");
        jFileChooser.setApproveButtonToolTipText("Select file");
        jFileChooser.setDialogTitle("File selection");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
