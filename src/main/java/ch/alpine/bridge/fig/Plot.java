// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class Plot extends BaseShowable {
  public enum Option {
    FILLING,
    STRICT
  }

  private static final int RESOLUTION = 40;
  private static final int FILL_ALPHA = 64;

  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain, Option... options) {
    return new Plot(suo, domain, options);
  }

  // ---
  private final ScalarUnaryOperator suo;
  private final Clip domain;
  private final Set<Option> set = EnumSet.noneOf(Option.class);

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  private Plot(ScalarUnaryOperator suo, Clip domain, Option... options) {
    this.suo = suo;
    this.domain = Objects.requireNonNull(domain);
    Stream.of(options).forEach(set::add);
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Clip x_clip = showableConfig.getClip(0);
    if (set.contains(Option.STRICT))
      x_clip = Clips.optionalIntersection(x_clip, domain).orElse(null);
    if (Objects.nonNull(x_clip)) {
      int segmentsPerPixel = 1;
      if (Sign.isPositive(x_clip.width())) {
        graphics.setColor(getColor());
        graphics.setStroke(getStroke());
        final double x0 = showableConfig.x_pos(x_clip.min());
        final double x1 = showableConfig.x_pos(x_clip.max());
        Path2D.Double path = new Path2D.Double();
        // TODO there is several interpolations concat here, also: enhance precision?
        path.moveTo(x0, showableConfig.y_pos(suo.apply(x_clip.min())));
        ScalarUnaryOperator interpX = LinearInterpolation.of(x_clip);
        final int size = (int) ((x1 - x0) * segmentsPerPixel);
        final double dx = 1.0 / segmentsPerPixel;
        double xc = x0;
        for (int i = 1; i <= size; ++i) {
          xc += dx;
          path.lineTo(xc, showableConfig.y_pos(suo.apply(interpX.apply(RationalScalar.of(i, size)))));
        }
        graphics.draw(path);
        if (set.contains(Option.FILLING)) {
          path.lineTo(x1, showableConfig.y_pos(suo.apply(x_clip.max()).zero()));
          path.lineTo(x0, showableConfig.y_pos(suo.apply(x_clip.min()).zero()));
          graphics.setColor(StaticHelper.withAlpha(getColor(), FILL_ALPHA));
          graphics.fill(path);
        }
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Sign.isPositive(domain.width())) {
      // TODO BRIDGE evaluating suo at points may throw an exception...
      Clip clip = StaticHelper.minMax(Subdivide.increasing(domain, RESOLUTION).maps(suo));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
