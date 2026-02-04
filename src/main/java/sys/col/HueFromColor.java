// code from Computer Graphics, by Donald Hearn and Pauline Baker
// adapted by jph
package sys.col;

import java.awt.Color;

import ch.alpine.tensor.ext.Hue;

/** @see Hue */
public record HueFromColor(double hue, double sat, double val, double alpha) {
  /** @param color
   * @return */
  public static HueFromColor of(Color color) {
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    int min = Math.min(r, Math.min(g, b));
    int max = Math.max(r, Math.max(g, b));
    double del = max - min;
    double sat = max == 0 ? 0 : del / max;
    double hue;
    if (sat == 0) {
      hue = 0;
    } else {
      if (r == max) {
        int dif = g - b;
        hue = (dif < 0 ? 6 : 0) + dif / del;
      } else //
      if (g == max)
        hue = 2 + (b - r) / del;
      else
        // b == max
        hue = 4 + (r - g) / del;
      hue /= 6;
    }
    return new HueFromColor(hue, sat, max / 255.0, color.getAlpha() / 255.0);
  }

  public Color modifHSV(double s, double v) {
    return Hue.of(hue(), sat() * s, val() * v, alpha());
  }

  public String toFriendlyString() {
    return String.format("(%5.3f,%5.3f,%5.3f,%5.3f)", hue, sat, val, alpha);
  }
}
