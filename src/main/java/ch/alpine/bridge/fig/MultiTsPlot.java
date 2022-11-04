// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.tmp.TimeSeries;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class MultiTsPlot extends BaseShowable {
  /** @param timeSeries
   * @param tuo */
  public static Showable of(TimeSeries timeSeries, TensorUnaryOperator tuo) {
    return of(timeSeries, tuo, ColorDataLists._097.cyclic());
  }

  public static Showable of(TimeSeries timeSeries, TensorUnaryOperator tuo, ColorDataIndexed colorDataIndexed) {
    return new MultiTsPlot(timeSeries, tuo, colorDataIndexed);
  }
  //

  /** @param timeSeries
   * @return */
  public static Showable of(TimeSeries timeSeries) {
    return of(timeSeries, TensorUnaryOperator.IDENTITY);
  }

  // ---
  private final TimeSeries timeSeries;
  private final TensorUnaryOperator tuo;
  private final ColorDataIndexed colorDataIndexed;

  private MultiTsPlot(TimeSeries timeSeries, TensorUnaryOperator tuo, ColorDataIndexed colorDataIndexed) {
    this.timeSeries = timeSeries;
    this.tuo = tuo;
    this.colorDataIndexed = colorDataIndexed;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (timeSeries.isEmpty())
      return;
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), timeSeries.domain());
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      if (Sign.isPositive(x_clip.width())) {
        ScalarTensorFunction suo = x -> tuo.apply(timeSeries.evaluate(x));
        Tensor v0 = suo.apply(x_clip.min());
        List<Path2D.Double> list = Stream.generate(() -> new Path2D.Double()).limit(v0.length()).toList();
        double x0 = showableConfig.x_pos(x_clip.min());
        for (int i = 0; i < v0.length(); ++i)
          list.get(i).moveTo(x0, showableConfig.y_pos(v0.Get(i)));
        // FIXME pan this in ShowComponentDemo and see that the border is incorrect
        // TODO draw until 1 element higher than right border (if exists), same 1 element lower
        timeSeries.block(x_clip, true).stream() //
            .forEach(tsEntry -> {
              double x1 = showableConfig.x_pos(tsEntry.key());
              Tensor v1 = tuo.apply(tsEntry.value());
              for (int i = 0; i < v1.length(); ++i)
                list.get(i).lineTo(x1, showableConfig.y_pos(v1.Get(i)));
            });
        graphics.setStroke(getStroke());
        for (int i = 0; i < v0.length(); ++i) {
          graphics.setColor(colorDataIndexed.getColor(i));
          graphics.draw(list.get(i));
        }
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return StaticHelper.fullPlotRange(timeSeries, tuo);
  }
}
