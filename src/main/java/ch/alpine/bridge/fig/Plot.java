// code by legion
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
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

  /** @param suo
   * @param domain
   * @return */
  @SuppressWarnings("unchecked")
  public static Showable of(ScalarUnaryOperator suo, Clip domain) {
    return new Plot(suo, domain);
  }

  // ---
  private final ScalarUnaryOperator suo;
  private final Clip domain;

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty */
  private Plot(ScalarUnaryOperator suo, Clip domain) {
    this.suo = suo;
    this.domain = domain;
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
