// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Optional;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TsEntry;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class TsPlot extends BaseShowable {
  private static final int RESOLUTION = 20;

  public static Showable of(TimeSeries timeSeries) {
    return of(timeSeries, Scalar.class::cast);
  }

  @SuppressWarnings("unchecked")
  public static Showable of(TimeSeries timeSeries, TensorScalarFunction tsf) {
    return new TsPlot(timeSeries, tsf);
  }

  // ---
  private final TimeSeries timeSeries;
  private final TensorScalarFunction tsf;

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty */
  private TsPlot(TimeSeries timeSeries, TensorScalarFunction tsf) {
    this.timeSeries = timeSeries;
    this.tsf = tsf;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (timeSeries.isEmpty())
      return;
    // TODO BRIDGE TsPlot should be improved
    // ... also handle vectors
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), timeSeries.domain());
    if (optional.isPresent()) {
      int segmentsPerPixel = 1;
      Clip x_clip = optional.orElseThrow();
      if (Sign.isPositive(x_clip.width())) {
        ScalarUnaryOperator suo = x -> tsf.apply(timeSeries.evaluate(x));
        graphics.setColor(getColor());
        graphics.setStroke(getStroke());
        double x0 = showableConfig.x_pos(x_clip.min());
        double x1 = showableConfig.x_pos(x_clip.max());
        Path2D.Double path = new Path2D.Double();
        {
          Scalar eval = suo.apply(x_clip.min());
          path.moveTo(x0, showableConfig.y_pos(eval));
        }
        ScalarUnaryOperator interpX = LinearInterpolation.of(x_clip);
        final int size = (int) ((x1 - x0) * segmentsPerPixel);
        final double dx = 1.0 / segmentsPerPixel;
        for (int i = 1; i <= size; ++i) {
          x0 += dx;
          // compute the xValue and yValue of the function at xPix
          Scalar y_eval = suo.apply(interpX.apply(RationalScalar.of(i, size)));
          path.lineTo(x0, showableConfig.y_pos(y_eval));
        }
        graphics.draw(path);
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return timeSeries.isEmpty() //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            timeSeries.domain(), //
            timeSeries.stream().map(TsEntry::value).map(tsf).collect(MinMax.toClip())));
  }
}
