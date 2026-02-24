// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.Integers;

/** DO NOT USE IN THE APPLICATION LAYER */
public enum SanityCheckRunProvider implements Consumer<RunProvider> {
  INSTANCE;

  private static final int WIDTH = 800;
  private static final int HEIGHT = 800;

  @Override
  public void accept(RunProvider runProvider) {
    switch (runProvider) {
    case WindowProvider windowProvider -> check(windowProvider);
    case ManipulateProvider manipulateProvider -> check(manipulateProvider);
    case ShowProvider showProvider -> check(showProvider);
    case VoidProvider voidProvider -> check(voidProvider);
    }
  }

  private void check(WindowProvider windowProvider) {
    Window window = windowProvider.getWindow();
    window.setSize(WIDTH, HEIGHT);
    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    window.printAll(graphics);
    graphics.dispose();
  }

  private void check(ManipulateProvider manipulateProvider) {
    Container jComponent = manipulateProvider.getContainer();
    jComponent.setSize(WIDTH, HEIGHT);
    jComponent.doLayout(); // mandatory
    int width = jComponent.getWidth();
    Integers.requireEquals(width, WIDTH);
    int height = jComponent.getHeight();
    Integers.requireEquals(height, HEIGHT);
    if (width == 0 || height == 0) {
      throw new IllegalStateException("Component must have a size");
    }
    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    jComponent.printAll(graphics);
    graphics.dispose();
  }

  private void check(ShowProvider showProvider) {
    Show show = showProvider.getShow();
    Dimension dimension = new Dimension(800, 800);
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    show.render_autoIndent(graphics, new Rectangle(dimension));
    graphics.dispose();
  }

  private void check(VoidProvider voidProvider) {
    voidProvider.runStandalone();
  }
}
