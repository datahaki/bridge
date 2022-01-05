// code by jph
package ch.alpine.java.ref;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import ch.alpine.javax.swing.LookAndFeels;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ResourceData;

public enum GuiExtensionDemo {
  ;
  public static void main(String[] args) throws Exception {
    int n = 22;
    String asd = "/image/checkbox/metro";
    FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(asd + "/0.png"), n, n)));
    FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(asd + "/1.png"), n, n)));
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsEditor = new PanelFieldsEditor(guiExtension);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    TestHelper testHelper = new TestHelper(fieldsEditor, guiExtension);
    // ---
    JFrame jFrame = new JFrame();
    // File root = GrzSettings.file("GuiExtension");
    // root.mkdirs();
    // WindowConfiguration.attach(jFrame, new File(root, "WindowConfiguration.properties"));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, testHelper.jPanel);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        testHelper.runnable.run();
        // fieldsEditor.list().forEach(fp->fp.notifyListeners(""));
      });
      jPanel.add("South", jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
