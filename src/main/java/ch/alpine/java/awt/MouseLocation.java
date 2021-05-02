// code by jph
package ch.alpine.java.awt;

import java.awt.MouseInfo;
import java.awt.Point;

public enum MouseLocation {
  ;
  public static Point getMouseLocation() {
    try {
      // can test with GraphicsEnvironment.isHeadless()
      return MouseInfo.getPointerInfo().getLocation();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return new Point();
  }
}
