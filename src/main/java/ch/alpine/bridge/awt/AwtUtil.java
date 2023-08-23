// code by jph
package ch.alpine.bridge.awt;

import java.awt.Dimension;
import java.awt.Point;

public enum AwtUtil {
  ;
  public static Point center(Dimension dimension) {
    return new Point(dimension.width / 2, dimension.height / 2);
  }
}
