// code by jph
package ch.alpine.bridge.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import ch.alpine.tensor.ext.Integers;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ScreenRectangle.html">ScreenRectangle</a> */
public class ScreenRectangle {
  private Rectangle screen = new Rectangle();

  public ScreenRectangle() {
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    for (GraphicsDevice graphicsDevice : graphicsEnvironment.getScreenDevices())
      for (GraphicsConfiguration graphicsConfiguration : graphicsDevice.getConfigurations())
        screen = screen.union(graphicsConfiguration.getBounds());
  }

  public Rectangle allVisible(int x, int y, int width, int height) {
    x = Integers.clip(0, screen.width - width).applyAsInt(x);
    y = Integers.clip(0, screen.height - height).applyAsInt(y);
    return new Rectangle(x, y, width, height);
  }

  public Rectangle allVisible(Rectangle rectangle) {
    return allVisible(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
  }

  public Rectangle getScreenRectangle() {
    return new Rectangle(screen);
  }

  @Override
  public String toString() {
    return "Screen point=(" + screen.x + ", " + screen.y + ") dimension=(" + screen.width + ", " + screen.height + ")";
  }
}
