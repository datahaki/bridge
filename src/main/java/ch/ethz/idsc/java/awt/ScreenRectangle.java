// code by jph
package ch.ethz.idsc.java.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public enum ScreenRectangle {
  INSTANCE;

  private Rectangle screen = new Rectangle();

  private ScreenRectangle() {
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    for (GraphicsDevice graphicsDevice : graphicsEnvironment.getScreenDevices())
      for (GraphicsConfiguration graphicsConfiguration : graphicsDevice.getConfigurations())
        screen = screen.union(graphicsConfiguration.getBounds());
  }

  public Rectangle allVisible(int x, int y, int width, int height) {
    x = Math.max(0, Math.min(x, screen.width - width));
    y = Math.max(0, Math.min(y, screen.height - height));
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
    return "Display point=(" + screen.x + ", " + screen.y + ") dimension=(" + screen.width + ", " + screen.height + ")";
  }
}
