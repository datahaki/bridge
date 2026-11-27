// code by jph
package ch.alpine.bridge.awt;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.demo.ref.GuiExtensionDemo;

class ContainerDescentTest {
  @Test
  void test() {
    GuiExtensionDemo guiExtensionDemo = new GuiExtensionDemo();
    RandomGenerator randomGenerator = ThreadLocalRandom.current();
    JFrame jFrame = guiExtensionDemo.getWindow();
    JRootPane jRootPane = jFrame.getRootPane();
    ContainerDescent.setEnabled(jRootPane, randomGenerator.nextBoolean());
    ContainerDescent.setEnabled(jRootPane, false);
    ContainerDescent.setEnabled(jRootPane, true);
    ContainerDescent.setEnabled(jRootPane, false);
  }
}
