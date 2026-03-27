// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.fig.Ticks;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Throw;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
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
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.cbb().clip(0), timeSeries.domain());
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      if (x_clip.isNonDegenerate()) {
        // TODO BRIDGE TsPlot should also handle vectors
        // timeSeries.block(x_clip, false).stream() //
        // .forEach(tsEntry -> tsf.apply(tsEntry.value()));
        NavigableSet<Scalar> navigableSet = asd(x_clip, showableConfig.rectangle().width);
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
              Point2D point0 = showableConfig.toPoint2D(Tensors.of(interval.min(), clip.max()));
              double x0 = point0.getX();
              double y0 = point0.getY();
              Point2D point1 = showableConfig.toPoint2D(Tensors.of(interval.max(), clip.min()));
              double x1 = point1.getX();
              double y1 = point1.getY();
              Throw.unless(y0 <= y1);
              graphics.fill(new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0));
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
    Scalar factor = Rational.of(MIN_SPACE, rectangle_width);
    if (clip.min() instanceof DateTime) {
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.length().multiply(factor));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      while (clip.isInside(dateTime)) {
        navigableMap.add(dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else
      Ticks.stream(clip, factor).forEach(navigableMap::add);
    return navigableMap;
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return StaticHelper.fullPlotRange(timeSeries, tsf);
  }
}
