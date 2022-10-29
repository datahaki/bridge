// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Optional;

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
  private static final int RESOLUTION = 20;
  private static final int ALPHA = 64;

  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain) {
    return new Plot(suo, domain, false);
  }

  /** used for plotting distributions
   * 
   * @param suo
   * @param domain
   * @return */
  public static Showable filling(ScalarUnaryOperator suo, Clip domain) {
    return new Plot(suo, domain, true);
  }

  // ---
  private final ScalarUnaryOperator suo;
  private final Clip domain;
  private final boolean fill;

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  private Plot(ScalarUnaryOperator suo, Clip domain, boolean fill) {
    this.suo = suo;
    this.domain = domain;
    this.fill = fill;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (Objects.nonNull(domain)) {
      Optional<Clip> optional = Clips.optionalIntersection(showableConfig.getClip(0), domain);
      if (optional.isPresent()) {
        int segmentsPerPixel = 1;
        Clip x_clip = optional.orElseThrow();
        if (Sign.isPositive(x_clip.width())) {
          graphics.setColor(getColor());
          graphics.setStroke(getStroke());
          final double x0 = showableConfig.x_pos(x_clip.min());
          final double x1 = showableConfig.x_pos(x_clip.max());
          Path2D.Double path = new Path2D.Double();
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
          if (fill) {
            path.lineTo(x1, showableConfig.y_pos(suo.apply(x_clip.max()).zero()));
            path.lineTo(x0, showableConfig.y_pos(suo.apply(x_clip.min()).zero()));
            graphics.setColor(StaticHelper.withAlpha(getColor(), ALPHA));
            graphics.fill(path);
          }
        }
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Objects.nonNull(domain) && Sign.isPositive(domain.width())) {
      Clip clip = StaticHelper.minMax(Subdivide.increasing(domain, RESOLUTION).map(suo));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
