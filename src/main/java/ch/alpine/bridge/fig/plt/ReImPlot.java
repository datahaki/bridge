// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.num.ReIm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ReImPlot.html">ReImPlot</a> */
public class ReImPlot extends UnaryShowable {
  public static final Stroke STROKE_RE = //
      new BasicStroke(1.5f);
  public static final Stroke STROKE_IM = //
      new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final int RESOLUTION = 20;
  private static final int ALPHA = 64;

  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain, PlotOption... options) {
    return new ReImPlot(suo, domain, options);
  }

  // ---
  private final Path2D.Double pathRe = new Path2D.Double();
  private final Path2D.Double pathIm = new Path2D.Double();

  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  private ReImPlot(ScalarUnaryOperator suo, Clip domain, PlotOption... options) {
    super(suo, domain, options);
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Optional<Clip> optional = x_clip(showableConfig);
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      int segmentsPerPixel = 1;
      if (Sign.isPositive(x_clip.width())) { // TODO this should not checking here!
        final double x0 = showableConfig.confX().pixel(x_clip.min());
        final double x1 = showableConfig.confX().pixel(x_clip.max());
        pathRe.reset();
        pathIm.reset();
        {
          ReIm reIm = ReIm.of(suo.apply(x_clip.min()));
          pathRe.moveTo(x0, showableConfig.confY().pixel(reIm.re()));
          pathIm.moveTo(x0, showableConfig.confY().pixel(reIm.im()));
        }
        ScalarUnaryOperator interpX = LinearInterpolation.of(x_clip);
        final int size = (int) ((x1 - x0) * segmentsPerPixel);
        final double dx = 1.0 / segmentsPerPixel;
        double xc = x0;
        for (int i = 1; i <= size; ++i) {
          xc += dx;
          ReIm reIm = ReIm.of(suo.apply(interpX.apply(Rational.of(i, size))));
          pathRe.lineTo(xc, showableConfig.confY().pixel(reIm.re()));
          pathIm.lineTo(xc, showableConfig.confY().pixel(reIm.im()));
        }
        graphics.setColor(getColor());
        graphics.setStroke(STROKE_RE);
        graphics.draw(pathRe);
        graphics.setStroke(STROKE_IM);
        graphics.draw(pathIm);
        if (set.contains(PlotOption.FILL)) {
          {
            double y1 = showableConfig.confY().pixel(suo.apply(x_clip.max()).zero());
            pathRe.lineTo(x1, y1);
            pathIm.lineTo(x1, y1);
          }
          {
            double y0 = showableConfig.confY().pixel(suo.apply(x_clip.min()).zero());
            pathRe.lineTo(x0, y0);
            pathIm.lineTo(x0, y0);
          }
          graphics.setColor(AwtUtil.withAlpha(getColor(), ALPHA));
          graphics.fill(pathRe);
          graphics.fill(pathIm);
        }
      }
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Sign.isPositive(domain.width())) {
      Clip clip = StaticHelper.minMax(Subdivide.increasing(domain, RESOLUTION) //
          .stream() //
          .map(Scalar.class::cast) //
          .map(suo) //
          .map(ReIm::of) //
          .flatMap(reIm -> Stream.of(reIm.re(), reIm.im())));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
