// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.mat.re.Pivots;

class SpinnerLabelTest {
  @Test
  public void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }

  @Test
  public void testEnum() {
    SpinnerLabel.of(Pivots.class);
  }

  @Test
  public void testFrame() throws InterruptedException {
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
