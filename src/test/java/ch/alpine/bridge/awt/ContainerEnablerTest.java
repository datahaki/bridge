// code by jph
package ch.alpine.bridge.awt;

import java.util.Random;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.GuiExtensionDemo;

class ContainerEnablerTest {
  @Test
  void test() {
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    Random random = new Random();
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, random.nextBoolean());
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, false);
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, true);
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, false);
  }
}
