// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TsEntry;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CandlestickChart.html">CandlestickChart</a> */
public class CandlestickChart extends BaseShowable {
  private static final int MIN_SPACE = 3;

  public static Showable of(TimeSeries timeSeries, TensorScalarFunction tsf) {
    return new CandlestickChart(timeSeries, tsf);
  }

  public static Showable of(TimeSeries timeSeries) {
    return of(timeSeries, Scalar.class::cast);
  }

  // ---
  private final TimeSeries timeSeries;
  private final TensorScalarFunction tsf;

  private CandlestickChart(TimeSeries timeSeries, TensorScalarFunction tsf) {
    this.timeSeries = timeSeries;
    this.tsf = tsf;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (timeSeries.isEmpty())
      return;
    // TODO BRIDGE TsPlot should also handle vectors
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), timeSeries.domain());
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      if (Sign.isPositive(x_clip.width())) {
        // TODO BRIDGE TsPlot should also handle vectors
        // timeSeries.block(x_clip, false).stream() //
        // .forEach(tsEntry -> tsf.apply(tsEntry.value()));
        NavigableSet<Scalar> navigableSet = asd(x_clip, showableConfig.rectangle.width);
        if (1 < navigableSet.size()) {
          // graphics.setColor(StaticHelper.withAlpha(getColor(), ALPHA));
          graphics.setColor(getColor());
          graphics.setStroke(getStroke());
          Iterator<Scalar> iterator = navigableSet.iterator();
          Scalar prev = iterator.next();
          while (iterator.hasNext()) {
            Scalar next = iterator.next();
            Clip interval = Clips.interval(prev, next);
            Clip clip = timeSeries.block(interval, false).stream() //
                .map(TsEntry::value) //
                .map(tsf).collect(MinMax.toClip());
            if (Objects.nonNull(clip)) {
              double x0 = showableConfig.x_pos(interval.min());
              double x1 = showableConfig.x_pos(interval.max());
              double y0 = showableConfig.y_pos(clip.max());
              double y1 = showableConfig.y_pos(clip.min());
              graphics.fillRect((int) x0, (int) y0, (int) (x1 - x0), (int) (y1 - y0 + 1));
            }
            prev = next;
          }
        }
      }
    }
  }

  // TODO BRIDGE refactor with showableConfig
  public static NavigableSet<Scalar> asd(Clip clip, int rectangle_width) {
    NavigableSet<Scalar> navigableMap = new TreeSet<>();
    if (clip.min() instanceof DateTime) {
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(RationalScalar.of(MIN_SPACE, rectangle_width)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      while (clip.isInside(dateTime)) {
        navigableMap.add(dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      Scalar dX = StaticHelper.getDecimalStep(clip.width().divide(RealScalar.of(rectangle_width)).multiply(RealScalar.of(MIN_SPACE)));
      for ( //
          Scalar xValue = Ceiling.toMultipleOf(dX).apply(clip.min()); //
          Scalars.lessEquals(xValue, clip.max()); //
          xValue = xValue.add(dX)) {
        navigableMap.add(xValue);
      }
    }
    return navigableMap;
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return StaticHelper.fullPlotRange(timeSeries, tsf);
  }
}
