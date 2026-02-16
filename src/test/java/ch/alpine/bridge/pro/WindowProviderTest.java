// code by jph
package ch.alpine.bridge.pro;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.ext.ref.ImplementationDiscovery;

class WindowProviderTest implements Consumer<WindowProvider> {
  @TempDir
  Path tempDir;

  @TestFactory
  Collection<DynamicTest> dynamicTests() {
    ImplementationDiscovery<WindowProvider> classDiscUtils = new ImplementationDiscovery<>(WindowProvider.class);
    List<WindowProvider> list = classDiscUtils.getInstances("ch.alpine");
    assertFalse(list.isEmpty());
    return list.stream() //
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
  }
}
