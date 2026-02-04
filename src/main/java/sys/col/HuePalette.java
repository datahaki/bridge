// code by jph
package sys.col;

import java.awt.Color;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.chq.FiniteTensorQ;
import ch.alpine.tensor.ext.ArgMin;
import ch.alpine.tensor.ext.Hue;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.sca.Mod;
import ch.alpine.tensor.sca.pow.Sqrt;

/** Quote from stackoverflow
 * 
 * Perceived brightness? Luminance?
 * (standard for certain color spaces): (0.2126*R + 0.7152*G + 0.0722*B)
 * (perceived option 1): (0.299*R + 0.587*G + 0.114*B)
 * (perceived option 2, slower to calculate): sqrt( 0.241*R^2 + 0.691*G^2 + 0.068*B^2 ) ? sqrt( 0.299*R^2 + 0.587*G^2 + 0.114*B^2 )
 * 
 * I think what you are looking for is the RGB -> Luma conversion formula.
 * 
 * Photometric/digital ITU-R:
 * Y = 0.2126 R + 0.7152 G + 0.0722 B
 * 
 * Digital CCIR601 (gives more weight to the R and B components):
 * Y = 0.299 R + 0.587 G + 0.114 B
 * 
 * If you are willing to trade accuracy for performance, there are two approximation formulas for this one:
 * Y = 0.33 R + 0.5 G + 0.16 B
 * Y = 0.375 R + 0.5 G + 0.125 B
 * 
 * These can be calculated quickly as
 * Y = (R+R+B+G+G+G)/6
 * Y = (R+R+R+B+G+G+G+G)>>3 */
public class HuePalette implements ColorDataIndexed {
  // { 0.30, 0.59, 0.11 };
  // { 0.40, 0.55, 0.25 };
  // { 0.45, 0.50, 0.35 };
  private static final Tensor WEIGHTS = Tensors.vector(0.45, 0.50, 0.4);
  /** luma */
  public static final HuePalette LUMA = of(ColorDataLists._250.cyclic());

  public static HuePalette of(ColorDataIndexed colorDataIndexed) {
    return new HuePalette(colorDataIndexed, WEIGHTS);
  }

  // ---
  private final ColorDataIndexed colorDataIndexed;
  private final Tensor hue;
  private final Tensor value;

  private HuePalette(ColorDataIndexed colorDataIndexed, Tensor weights) {
    this.colorDataIndexed = colorDataIndexed;
    // ---
    hue = Tensors.reserve(length());
    Tensor vector = Tensors.reserve(length());
    for (int index = 0; index < colorDataIndexed.length(); ++index) {
      Color color = colorDataIndexed.getColor(index);
      hue.append(RealScalar.of(HueFromColor.of(color).hue()));
      vector.append(fakeITU(weights, color));
    }
    if (FiniteTensorQ.of(vector)) {
      Scalar min = vector.Get(ArgMin.of(vector));
      value = vector.map(min::divide);
    } else
      value = vector;
  }

  private static Scalar fakeITU(Tensor weights, Color color) {
    Tensor vector = ColorFormat.toVector(color).extract(0, 3);
    return Sqrt.FUNCTION.apply((Scalar) weights.dot(vector)).divide(Vector2Norm.of(vector));
  }

  @Override
  public Color getColor(int index) {
    return getColor(index, 1, 1, 1);
  }

  @Override
  public int length() {
    return colorDataIndexed.length();
  }

  @Override
  public Tensor apply(Scalar scalar) {
    int index = Scalars.intValueExact(Mod.function(length()).apply(scalar));
    return ColorFormat.toVector(getColor(index));
  }

  public Color getColor(int index, double sat, double val, double alpha) {
    int mod = Math.floorMod(index, length());
    // TODO MIDI need reference for fancy formula below!
    return Hue.of(hue.Get(mod).number().doubleValue(), sat, val * Math.pow(value.Get(mod).number().doubleValue(), sat), alpha);
  }

  @Override
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    throw new UnsupportedOperationException();
  }
}
