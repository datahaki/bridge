// code by jph
package ch.alpine.bridge.fig.plt;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** used by {@link Plot} and {@link ReImPlot} */
public abstract class UnaryShowable extends BaseShowable {
  protected final ScalarUnaryOperator suo;
  protected final Clip domain;

  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  protected UnaryShowable(ScalarUnaryOperator suo, Clip domain, PlotOption... plotOptions) {
    this.suo = suo;
    this.domain = Objects.requireNonNull(domain);
    Arrays.stream(plotOptions).forEach(set::add);
  }

  protected final Optional<Clip> x_clip(ShowableConfig showableConfig) {
    Clip x_clip = showableConfig.confX().clip();
    return set.contains(PlotOption.STRICT) //
        ? Clips.optionalIntersection(x_clip, domain).filter(Clip::isNonDegenerate)
        : Optional.ofNullable(x_clip);
  }
}
