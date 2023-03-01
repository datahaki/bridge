// code by jph
package ch.alpine.bridge.awt;

import java.util.Random;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.usr.GuiExtensionDemo;

class ContainerEnablerTest {
  @Test
  void test() {
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    RandomGenerator random = new Random();
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, random.nextBoolean());
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, false);
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, true);
    ContainerEnabler.setEnabled(guiExtensionDemo.jSplitPane, false);
  }
}
