// code by jph
package ch.alpine.bridge.awt;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.usr.GuiExtensionDemo;

class ContainerDescentTest {
  @Test
  void test() {
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    RandomGenerator random = new SecureRandom();
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, random.nextBoolean());
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, false);
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, true);
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, false);
  }
}
