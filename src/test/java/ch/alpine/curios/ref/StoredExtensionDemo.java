// code by jph
package ch.alpine.curios.ref;

import java.awt.GridLayout;
import java.awt.Window;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.StoredExtension;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class StoredExtensionDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    Path path = resourceLocator.properties(StoredExtension.class);
    StoredExtension storedExtension = ObjectProperties.tryLoad(new StoredExtension(), path);
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(storedExtension);
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, storedExtension);
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jGrid);
    jFrame.setBounds(500, 200, 500, 700);
    WindowClosed.runs(jFrame, () -> ObjectProperties.trySave(storedExtension, path));
    return jFrame;
  }

  static void main() {
    new StoredExtensionDemo().runStandalone();
  }
}
