// code by jph
package ch.alpine.bridge.fig.grd;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.bridge.fig.tic.Ticks;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.DateTime;
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
  // TODO 100 is a magic constant that should depend on font, and date formatter
  private static final int REF = 100;
  private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);

  /** @param pixel_ofs
   * @param pixel_len
   * @param axisMap
   * @param model_clip
   * @param incr
   * @return */
  public static AxisConfig create(int pixel_ofs, int pixel_len, AxisMap axisMap, Clip model_clip, boolean incr) {
    Clip range_clip;
    {
      Scalar r1 = axisMap.model2range(model_clip.min());
      Scalar r2 = axisMap.model2range(model_clip.max());
      range_clip = Clips.interval(Min.of(r1, r2), Max.of(r1, r2));
    }
    Scalar range_beg = incr ? range_clip.min() : range_clip.max();
    Scalar range_end = incr ? range_clip.max() : range_clip.min();
    Scalar sw = RealScalar.of(pixel_len - 1);
    ScalarUnaryOperator unitConvert = UnitConvert.SI().to(QuantityUnit.of(model_clip));
    Scalar range2pixel = sw.divide(range_end.subtract(range_beg));
    Scalar pixel2range = range_end.subtract(range_beg).divide(sw);
    return new AxisConfig(pixel_ofs, pixel_len, axisMap, model_clip, range_clip, range_beg, range_end, unitConvert, range2pixel, pixel2range);
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

  public Clip ticks_clip() {
    return axisMap.tickOption().equals(TickOption.RANGE) //
        ? range_clip()
        : model_clip();
  }

  public double ticks2pixel(Scalar ticks) {
    return axisMap.tickOption().equals(TickOption.RANGE) //
        ? range2pixel(ticks)
        : model2pixel(ticks);
  }

  public String format(Scalar ticks) {
    return axisMap.format(ticks);
  }

  public Map<Integer, String> getTicksMap(boolean vertical, Font font, DateTimeFocus dateTimeFocus, boolean gridLines) {
    Map<Integer, String> map = new HashMap<>();
    Clip ticks_clip = ticks_clip();
    if (ticks_clip.isNonDegenerate()) {
      if (ticks_clip.min() instanceof DateTime) {
        DateTimeInterval dateTimeInterval = //
            DateTimeInterval.findAboveEquals(ticks_clip.length().multiply(Rational.of(REF, pixel_len())));
        DateTime startAttempt = dateTimeInterval.floor(ticks_clip.min());
        DateTime dateTime = ticks_clip.isInside(startAttempt) //
            ? startAttempt
            : dateTimeInterval.plus(startAttempt);
        DateTimeFormatter dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
        while (ticks_clip.isInside(dateTime)) {
          int pixel = (int) ticks2pixel(dateTime);
          map.put(pixel, dateTime.format(dateTimeFormatter));
          dateTime = dateTimeInterval.plus(dateTime);
        }
      } else {
        int val = 50;
        int rem = pixel_len() / 3;
        if (vertical && rem < val) {
          LineMetrics lineMetrics = font.getLineMetrics("Ag", FONT_RENDER_CONTEXT);
          val = Math.max((int) Math.ceil(lineMetrics.getAscent() + lineMetrics.getDescent()), rem);
        }
        Ticks ref = gridLines //
            ? axisMap.ticks().subTicks()
            : axisMap.ticks();
        List<Scalar> list = ref.stream(ticks_clip, Rational.of(val, pixel_len())).toList();
        if (gridLines)
          list.forEach(ticks -> map.put((int) ticks2pixel(ticks), null));
        else
          list.forEach(ticks -> map.put((int) ticks2pixel(ticks), format(ticks)));
      }
    }
    return map;
  }
}
