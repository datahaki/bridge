// code by jph
package ch.alpine.bridge.pro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.tensor.ext.ref.InstanceDiscovery;

class WindowProviderTest implements Consumer<WindowProvider> {
  private static final AtomicInteger COUNT = new AtomicInteger();

  @TestFactory
  Collection<DynamicTest> dynamicTests() {
    return InstanceDiscovery.of("ch.alpine", WindowProvider.class).stream() //
        .map(instance -> DynamicTest.dynamicTest(instance.toString(), () -> accept(instance))) //
        .toList();
  }

  @Override
  public void accept(WindowProvider showProvider) {
    // IO.println(tempDir);
    // IO.println(showProvider);
    Window window = showProvider.getWindow();
    window.setSize(800, 800);
    window.doLayout(); // mandatory
    int width = window.getWidth();
    int height = window.getHeight();
    if (width == 0 || height == 0)
      throw new IllegalStateException("Component must have a size");
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    window.printAll(graphics);
    graphics.dispose();
    COUNT.getAndIncrement();
  }

  @AfterAll
  static void here() {
    assertTrue(13 <= COUNT.get());
  }
}
