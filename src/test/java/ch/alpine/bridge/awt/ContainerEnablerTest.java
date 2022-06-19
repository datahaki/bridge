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
    ContainerEnabler.setEnabled(guiExtensionDemo.jGrid, random.nextBoolean());
    ContainerEnabler.setEnabled(guiExtensionDemo.jGrid, false);
    ContainerEnabler.setEnabled(guiExtensionDemo.jGrid, true);
    ContainerEnabler.setEnabled(guiExtensionDemo.jGrid, false);
  }
}
