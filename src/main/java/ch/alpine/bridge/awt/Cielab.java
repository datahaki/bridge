// code by jph
package ch.alpine.bridge.awt;

import java.awt.Color;
import java.awt.color.ColorSpace;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.io.Primitives;
import ch.alpine.tensor.itp.BinaryAverage;
import ch.alpine.tensor.itp.LinearBinaryAverage;
import ch.alpine.tensor.red.Times;

/** Hint:
 * The xyz values can be obtained via the following {@link ColorSpace}
 * ColorSpace.getInstance(ColorSpace.CS_CIEXYZ)
 * 
 * <p>Reference:
 * https://en.wikipedia.org/wiki/CIELAB_color_space */
public enum Cielab {
  /** Standard Illuminant D65 */
  D65(0.950489, 1, 1.088840), //
  /** Illuminant D50 (used in the printing industry) */
  D50(0.964212, 1, 0.825188), //
  ;

  private static final ColorSpace COLOR_SPACE = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
  private static final Scalar _016 = RealScalar.of(0.16);
  private static final Scalar _116 = RealScalar.of(1.16);
  private static final Scalar _500 = RealScalar.of(5.00);
  private static final Scalar _200 = RealScalar.of(2.00);
  // ---
  private final Tensor xyzn;
  private final Tensor xyzi;

  private Cielab(double x, double y, double z) {
    xyzn = Tensors.vectorDouble(x, y, z);
    xyzi = this.xyzn.map(Scalar::reciprocal);
  }

  /** @param xyz vector of length 3 with entries in unit interval
   * @return vector of length 3 with entries in [-1, 1] */
  public Tensor xyz2lab(Tensor xyz) {
    Tensor fs3 = Times.of(xyz, xyzi).map(Cielabf::forward);
    Scalar L = _116.multiply(fs3.Get(1)).subtract(_016);
    Scalar a = _500.multiply(fs3.Get(0).subtract(fs3.Get(1)));
    Scalar b = _200.multiply(fs3.Get(1).subtract(fs3.Get(2)));
    return Tensors.of(L, a, b);
  }

  /** @param lab vector of length 3 with entries in [-1, 1]
   * @return vector of length 3 with entries in unit interval */
  public Tensor lab2xyz(Tensor lab) {
    Integers.requireEquals(lab.length(), 3);
    Scalar L = lab.Get(0);
    Scalar a = lab.Get(1);
    Scalar b = lab.Get(2);
    Scalar p = L.add(_016).divide(_116);
    Scalar x = Cielabf.inverse(p.add(a.divide(_500)));
    Scalar y = Cielabf.inverse(p);
    Scalar z = Cielabf.inverse(p.subtract(b.divide(_200)));
    return Times.of(Tensors.of(x, y, z), xyzn);
  }

  /** @param color1
   * @param color2
   * @param scalar
   * @return color that interpolates between color1 and color2 at given parameter index
   * @see BinaryAverage */
  public Color split(Color color1, Color color2, Scalar scalar) {
    float[] rgba1 = color1.getComponents(null); // values are in the unit interval
    float[] rgba2 = color2.getComponents(null);
    float[] xyz1 = COLOR_SPACE.fromRGB(rgba1); // length 3
    float[] xyz2 = COLOR_SPACE.fromRGB(rgba2);
    Tensor lab1 = xyz2lab(Tensors.vectorFloat(xyz1));
    Tensor lab2 = xyz2lab(Tensors.vectorFloat(xyz2));
    Tensor lab = LinearBinaryAverage.INSTANCE.split(lab1, lab2, scalar);
    Tensor xyz = lab2xyz(lab);
    float[] rgb = COLOR_SPACE.toRGB(Primitives.toFloatArray(xyz));
    Scalar a1 = RealScalar.of(rgba1[3]);
    Scalar a2 = RealScalar.of(rgba2[3]);
    Scalar alpha = a2.subtract(a1).multiply(scalar).add(a1);
    return ColorFormat.toColor(Tensors.vectorFloat(rgb).append(alpha).multiply(RealScalar.of(255)));
  }
}
