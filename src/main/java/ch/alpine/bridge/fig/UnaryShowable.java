// code by jph
package ch.alpine.bridge.fig;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import ch.alpine.bridge.fig.Plot.Option;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** used by {@link Plot} and {@link ReImPlot} */
public abstract class UnaryShowable extends BaseShowable {
  protected final ScalarUnaryOperator suo;
  protected final Clip domain;
  private final Set<Option> set = EnumSet.noneOf(Option.class);

  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  protected UnaryShowable(ScalarUnaryOperator suo, Clip domain, Option... options) {
    this.suo = suo;
    this.domain = Objects.requireNonNull(domain);
    Stream.of(options).forEach(set::add);
  }

  protected final Optional<Clip> x_clip(ShowableConfig showableConfig) {
    Clip x_clip = showableConfig.getClip(0);
    if (set.contains(Option.STRICT))
      x_clip = Clips.optionalIntersection(x_clip, domain).orElse(null);
    return Optional.ofNullable(x_clip);
  }

  protected final boolean isFilling() {
    return set.contains(Option.FILLING);
  }
}
