// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.num.ReIm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Im;
import ch.alpine.tensor.sca.Re;
import ch.alpine.tensor.sca.Sign;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ReImPlot.html">ReImPlot</a> */
public class ReImPlot extends BaseShowable {
  public static final Stroke STROKE_RE = //
      new BasicStroke(1.5f);
  public static final Stroke STROKE_IM = //
      new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final int RESOLUTION = 20;
  private static final int ALPHA = 64;

  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain) {
    return new ReImPlot(suo, domain, false);
  }

  /** used for plotting distributions
   * 
   * @param suo
   * @param domain
   * @return */
  public static Showable filling(ScalarUnaryOperator suo, Clip domain) {
    return new ReImPlot(suo, domain, true);
  }

  // ---
  private final ScalarUnaryOperator suo;
  private final Clip domain;
  private final boolean fill;

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  private ReImPlot(ScalarUnaryOperator suo, Clip domain, boolean fill) {
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
          final double x0 = showableConfig.x_pos(x_clip.min());
          final double x1 = showableConfig.x_pos(x_clip.max());
          Path2D.Double pathRe = new Path2D.Double();
          Path2D.Double pathIm = new Path2D.Double();
          {
            Scalar eval = suo.apply(x_clip.min());
            pathRe.moveTo(x0, showableConfig.y_pos(Re.FUNCTION.apply(eval)));
            pathIm.moveTo(x0, showableConfig.y_pos(Im.FUNCTION.apply(eval)));
          }
          ScalarUnaryOperator interpX = LinearInterpolation.of(x_clip);
          final int size = (int) ((x1 - x0) * segmentsPerPixel);
          final double dx = 1.0 / segmentsPerPixel;
          double xc = x0;
          for (int i = 1; i <= size; ++i) {
            xc += dx;
            Scalar eval = suo.apply(interpX.apply(RationalScalar.of(i, size)));
            pathRe.lineTo(xc, showableConfig.y_pos(Re.FUNCTION.apply(eval)));
            pathIm.lineTo(xc, showableConfig.y_pos(Im.FUNCTION.apply(eval)));
          }
          graphics.setColor(getColor());
          graphics.setStroke(STROKE_RE);
          graphics.draw(pathRe);
          graphics.setStroke(STROKE_IM);
          graphics.draw(pathIm);
          if (fill) {
            {
              double y1 = showableConfig.y_pos(suo.apply(x_clip.max()).zero());
              pathRe.lineTo(x1, y1);
              pathIm.lineTo(x1, y1);
            }
            {
              double y0 = showableConfig.y_pos(suo.apply(x_clip.min()).zero());
              pathRe.lineTo(x0, y0);
              pathIm.lineTo(x0, y0);
            }
            graphics.setColor(StaticHelper.withAlpha(getColor(), ALPHA));
            graphics.fill(pathRe);
            graphics.fill(pathIm);
          }
        }
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Objects.nonNull(domain) && Sign.isPositive(domain.width())) {
      Clip clip = StaticHelper.minMax(Subdivide.increasing(domain, RESOLUTION) //
          .stream() //
          .map(Scalar.class::cast) //
          .map(suo) //
          .flatMap(ReIm::stream));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
