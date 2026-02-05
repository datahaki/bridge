// code by jph
package showcase.demo;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.awt.ContainerDescent;

class ContainerDescentTest {
  @Test
  void test() {
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    RandomGenerator randomGenerator = ThreadLocalRandom.current();
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, randomGenerator.nextBoolean());
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, false);
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, true);
    ContainerDescent.setEnabled(guiExtensionDemo.jSplitPane, false);
  }
}
