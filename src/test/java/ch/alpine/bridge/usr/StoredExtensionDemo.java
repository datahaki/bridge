// code by jph
package ch.alpine.bridge.usr;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ex.StoredExtension;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.ext.HomeDirectory;

public enum StoredExtensionDemo {
  ;
  private static final File FILE = HomeDirectory.Downloads(StoredExtension.class.getSimpleName() + ".properties");
  public static final StoredExtension INSTANCE = //
      ObjectProperties.tryLoad(new StoredExtension(), FILE);

  public static void main(String[] args) {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(INSTANCE);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, INSTANCE);
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jGrid);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent windowEvent) {
        ObjectProperties.trySave(INSTANCE, FILE);
      }
    });
    jFrame.setVisible(true);
  }
}
