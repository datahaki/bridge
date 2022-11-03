// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
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
    // TODO BRIDGE TsPlot should also handle vectors
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), timeSeries.domain());
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      if (Sign.isPositive(x_clip.width())) {
        ScalarUnaryOperator suo = x -> tsf.apply(timeSeries.evaluate(x));
        Path2D path = new Path2D.Double();
        path.moveTo( //
            showableConfig.x_pos(x_clip.min()), //
            showableConfig.y_pos(suo.apply(x_clip.min())));
        // TODO BRIDGE TsPlot should also handle vectors
        timeSeries.block(x_clip, false).stream() //
            .forEach(tsEntry -> path.lineTo( //
                showableConfig.x_pos(tsEntry.key()), //
                showableConfig.y_pos(tsf.apply(tsEntry.value()))));
        graphics.setColor(getColor());
        graphics.setStroke(getStroke());
        graphics.draw(path);
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return StaticHelper.fullPlotRange(timeSeries, tsf);
  }
}
