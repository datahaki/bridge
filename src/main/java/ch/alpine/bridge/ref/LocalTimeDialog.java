// code by jph
package ch.alpine.bridge.ref;

import java.awt.Component;
import java.time.LocalTime;
import java.util.function.Consumer;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

// TODO BRIDGE not yet good
public class LocalTimeDialog extends JDialog {
  public LocalTimeDialog(Component c, Consumer<LocalTime> consumer) {
    super(JOptionPane.getFrameForComponent(c));
    LocalTimeParam localTimeParam = new LocalTimeParam();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
    setContentPane(panelFieldsEditor.createJScrollPane());
    panelFieldsEditor.addUniversalListener(() -> consumer.accept(localTimeParam.toLocalTime()));
  }
}
