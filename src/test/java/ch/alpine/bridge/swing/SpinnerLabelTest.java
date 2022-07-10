// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.mat.re.Pivots;

class SpinnerLabelTest {
  @Test
  void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }

  @Test
  void testEnum() {
    SpinnerLabel.of(Pivots.class);
  }

  @Test
  void testFrame() throws InterruptedException {
    JFrame jFrame = new JFrame();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
    jFrame.setBounds(100, 100, 400, 400);
    jFrame.setVisible(true);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }
}
