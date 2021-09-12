// code by jph
package ch.alpine.java.awt;

import java.awt.Color;

/* package */ enum StaticHelper {
  ;
  public static Color alpha064(Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
  }

  public static Color alpha128(Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);
  }

  public static Color withAlpha(Color color, int alpha) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
  }
}
