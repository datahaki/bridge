// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.tmp.TimeSeries;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class TsPlot extends BaseShowable {
  /** @param timeSeries
   * @param tsf maps a value to a scalar */
  public static Showable of(TimeSeries timeSeries, TensorScalarFunction tsf) {
    return new TsPlot(timeSeries, tsf);
  }

  /** @param timeSeries with values of type {@link Scalar}
   * @return */
  public static Showable of(TimeSeries timeSeries) {
    return of(timeSeries, Scalar.class::cast);
  }

  // ---
  private final TimeSeries timeSeries;
  private final TensorScalarFunction tsf;

  private TsPlot(TimeSeries timeSeries, TensorScalarFunction tsf) {
    this.timeSeries = timeSeries;
    this.tsf = tsf;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (timeSeries.isEmpty())
      return;
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), timeSeries.domain());
    if (optional.isPresent()) {
      Clip clip2 = optional.orElseThrow();
      if (Sign.isPositive(clip2.width())) {
        // Clip clip = StaticHelper.extend(timeSeries, clip2);
        // ScalarUnaryOperator suo = x -> tsf.apply(timeSeries.evaluate(x));
        graphics.setColor(getColor());
        graphics.setStroke(getStroke());
        timeSeries.lines().forEach(t -> {
          Path2D path = new Path2D.Double();
          Tensor tsFirst = t.get(0);
          path.moveTo( //
              showableConfig.x_pos(tsFirst.Get(0)), //
              showableConfig.y_pos(tsf.apply(tsFirst.get(1))));
          t.stream() //
              .skip(1) //
              .forEach(tsEntry -> path.lineTo( //
                  showableConfig.x_pos(tsEntry.Get(0)), //
                  showableConfig.y_pos(tsf.apply(tsEntry.get(1)))));
          graphics.draw(path);
        });
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return StaticHelper.fullPlotRange(timeSeries, tsf);
  }
}
