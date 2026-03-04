// code by jph
package ch.alpine.bridge.demo.ref;

import java.awt.GridLayout;
import java.awt.Window;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
    JPanel jPanel = new JPanel(new GridLayout(2, 1));
    jPanel.add(panelFieldsEditor.createJScrollPane());
    jPanel.add(objectPropertiesArea.createJComponent());
    jFrame.setContentPane(jPanel);
    WindowClosed.runs(jFrame, () -> ObjectProperties.trySave(storedExtension, path));
    return jFrame;
  }

  static void main() {
    new StoredExtensionDemo().runStandalone();
  }
}
