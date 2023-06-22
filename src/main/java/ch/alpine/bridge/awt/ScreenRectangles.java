// code by jph
package ch.alpine.bridge.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Stream;

import ch.alpine.tensor.ext.Integers;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ScreenRectangle.html">ScreenRectangle</a> */
public record ScreenRectangles(List<Rectangle> rectangles) {
  public static ScreenRectangles create() {
    return new ScreenRectangles(Stream.of(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) //
        .map(GraphicsDevice::getDefaultConfiguration) //
        .map(GraphicsConfiguration::getBounds) //
        .toList());
  }

  /** @param window
   * @return rectangle that is entirely contained inside one of the available screen rectangles */
  public Rectangle allVisible(Rectangle window) {
    Rectangle available = rectangles.stream() //
        .filter(rectangle -> !rectangle.intersection(window).isEmpty()) //
        .findFirst() //
        .orElse(rectangles.stream().findFirst().orElse(window));
    int width = Integers.clip(0, available.width).applyAsInt(window.width);
    int height = Integers.clip(0, available.height).applyAsInt(window.height);
    int x = Integers.clip(available.x, available.x + Math.max(0, available.width - width)).applyAsInt(window.x - (window.width - width));
    int y = Integers.clip(available.y, available.y + Math.max(0, available.height - height)).applyAsInt(window.y - (window.height - height));
    return new Rectangle(x, y, width, height);
  }
}
