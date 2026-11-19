// code by jph
package ch.alpine.bridge.fig.grd;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.red.Min;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** model -> range -> pixel
 * 
 * the mappings:
 * model -> range is defined by AxisMap
 * range -> pixel is affine linear
 * 
 * @param pixel_ofs start of drawing area
 * @param pixel_len greater equals 2
 * @param model_clip
 * @param range_beg model value at pixel ofs
 * @param range_end model value at pixel ofs + length - 1 */
public record AxisConfig( //
    int pixel_ofs, int pixel_len, //
    AxisMap axisMap, //
    Clip model_clip, Clip range_clip, //
    Scalar range_beg, Scalar range_end, //
    ScalarUnaryOperator unitConvert, //
    Scalar range2pixel, //
    Scalar pixel2range //
) implements Serializable {
  /** @param ofs
   * @param length
   * @param axisMap
   * @param model_clip
   * @param incr
   * @return */
  public static AxisConfig create(int ofs, int length, AxisMap axisMap, Clip model_clip, boolean incr) {
    Clip range_clip;
    {
      Scalar r1 = axisMap.model2range(model_clip.min());
      Scalar r2 = axisMap.model2range(model_clip.max());
      range_clip = Clips.interval(Min.of(r1, r2), Max.of(r1, r2));
    }
    Scalar range_beg = incr ? range_clip.min() : range_clip.max();
    Scalar range_end = incr ? range_clip.max() : range_clip.min();
    Scalar sw = RealScalar.of(length - 1);
    ScalarUnaryOperator unitConvert = UnitConvert.SI().to(QuantityUnit.of(model_clip));
    Scalar range2pixel = sw.divide(range_end.subtract(range_beg));
    Scalar pixel2range = range_end.subtract(range_beg).divide(sw);
    return new AxisConfig(ofs, length, axisMap, model_clip, range_clip, range_beg, range_end, unitConvert, range2pixel, pixel2range);
  }

  static AxisConfig linear(int ofs, int length, Clip model_clip, boolean incr) {
    return create(ofs, length, AxisMaps.LIN, model_clip, incr);
  }

  /** @param model in model space
   * @return pixel coordinate of value according to grid */
  public double model2pixel(Scalar model) {
    return range2pixel(axisMap.model2range(unitConvert.apply(model)));
  }

  double range2pixel(Scalar range) {
    return pixel_ofs + range.subtract(range_beg).multiply(range2pixel).number().doubleValue();
  }

  public Scalar pixel2range(int pixel) {
    return range_beg.add(RealScalar.of(pixel - pixel_ofs).multiply(pixel2range));
  }

  public Scalar pixel2model(int pixel) {
    return axisMap.range2model(pixel2range(pixel));
  }

  /** @return this (ConfIncr or ConfDecr) but with ofs == 0 */
  public AxisConfig pruned() {
    return new AxisConfig( //
        0, pixel_len, //
        axisMap, //
        model_clip, range_clip, //
        range_beg, range_end, //
        unitConvert, //
        range2pixel, //
        pixel2range);
  }

  /** @param pixel0 old
   * @param pixel1 new
   * @return */
  public Scalar shift(int pixel0, int pixel1) {
    return RealScalar.of(pixel0 - pixel1).multiply(pixel2range);
  }
}
