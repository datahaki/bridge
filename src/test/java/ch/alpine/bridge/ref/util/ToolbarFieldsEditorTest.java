// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.ex.OtherPackageParam;
import ch.alpine.bridge.ref.ex.SliderFailParam;

class ToolbarFieldsEditorTest {
  @Test
  void testSimple() {
    FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(new GuiExtension(), new JToolBar());
    fieldsEditor.updateJComponents();
  }

  @Test
  void testEmptyToolbar() {
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    JToolBar jToolBar = new JToolBar();
    FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(otherPackageParam, jToolBar);
    assertTrue(fieldsEditor.list().isEmpty());
    assertEquals(jToolBar.getComponentCount(), 0);
  }

  @Test
  void testChallenge() throws InterruptedException {
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.function = null;
    // guiExtension.status = null;
    guiExtension.status2 = null;
    guiExtension.cdg = null;
    guiExtension.background = null;
    guiExtension.file = null;
    guiExtension.clip = null;
    JToolBar jToolBar = new JToolBar();
    ToolbarFieldsEditor.add(guiExtension, jToolBar);
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(jToolBar);
    jFrame.setBounds(100, 100, 1000, 100);
    jFrame.setVisible(true);
    Thread.sleep(100);
    jFrame.setVisible(false);
    assertNull(guiExtension.function);
    // assertNull(guiExtension.status);
    assertNull(guiExtension.cdg);
    assertNull(guiExtension.background);
    assertNull(guiExtension.file);
    assertNull(guiExtension.clip);
  }

  @Test
  void testFrame() throws InterruptedException {
    GuiExtension guiExtension = new GuiExtension();
    JToolBar jToolBar = new JToolBar();
    ToolbarFieldsEditor.add(guiExtension, jToolBar);
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(jToolBar);
    jFrame.setBounds(100, 100, 1000, 100);
    jFrame.setVisible(true);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }

  @Test
  void testSliderFailToolbar() {
    SliderFailParam sliderFailParam = new SliderFailParam();
    assertThrows(Exception.class, () -> ToolbarFieldsEditor.add(sliderFailParam, new JToolBar()));
  }
}
