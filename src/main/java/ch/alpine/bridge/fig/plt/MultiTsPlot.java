// code by legion
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.col.ColorDataIndexed;
import ch.alpine.tensor.col.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.tmp.TimeSeries;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class MultiTsPlot extends BaseShowable {
  public static Showable of(TimeSeries timeSeries, TensorUnaryOperator tuo, ColorDataIndexed colorDataIndexed) {
    return new MultiTsPlot(timeSeries, tuo, colorDataIndexed);
  }

  /** @param timeSeries
   * @param tuo */
  public static Showable of(TimeSeries timeSeries, TensorUnaryOperator tuo) {
    return of(timeSeries, tuo, ColorDataLists._097.cyclic());
  }

  /** @param timeSeries
   * @return */
  public static Showable of(TimeSeries timeSeries) {
    return of(timeSeries, t -> t);
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
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.confX().clip(), timeSeries.domain());
    if (optional.isPresent()) {
      Clip clip = optional.orElseThrow();
      if (clip.isNonDegenerate()) {
        clip = StaticHelper.extend(timeSeries, clip);
        ScalarTensorFunction suo = x -> tuo.apply(timeSeries.evaluate(x));
        Tensor v0 = suo.apply(clip.min());
        List<Path2D.Double> list = Stream.generate(Path2D.Double::new).limit(v0.length()).toList();
        double x0 = showableConfig.confX().pixel(clip.min());
        for (int i = 0; i < v0.length(); ++i)
          list.get(i).moveTo(x0, showableConfig.confY().pixel(v0.Get(i)));
        timeSeries.block(clip, true).stream() //
            .forEach(tsEntry -> {
              double x1 = showableConfig.confX().pixel(tsEntry.key());
              Tensor v1 = tuo.apply(tsEntry.value());
              for (int i = 0; i < v1.length(); ++i)
                list.get(i).lineTo(x1, showableConfig.confY().pixel(v1.Get(i)));
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
