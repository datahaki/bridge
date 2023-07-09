// code by jph
package ch.alpine.bridge.usr;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.WindowClosed;
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
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(INSTANCE);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, INSTANCE);
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jGrid);
    jFrame.setBounds(500, 200, 500, 700);
    WindowClosed.runs(jFrame, () -> ObjectProperties.trySave(INSTANCE, FILE));
    jFrame.setVisible(true);
  }
}
