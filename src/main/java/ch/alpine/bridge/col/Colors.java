// code by jph
package ch.alpine.bridge.col;

import java.awt.Color;

public enum Colors {
  ;
  public static Color withAlpha(Color color, int alpha) {
    return new Color( //
        color.getRed(), //
        color.getGreen(), //
        color.getBlue(), //
        alpha);
  }
}
