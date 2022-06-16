// code by jph
package ch.alpine.bridge.awt;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Optional;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MousePosition.html">MousePosition</a> */
public enum MousePosition {
  ;
  public static Optional<Point> get() {
    try {
      // can test with GraphicsEnvironment.isHeadless()
      return Optional.of(MouseInfo.getPointerInfo().getLocation());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return Optional.empty();
  }
}
