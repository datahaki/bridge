// code by legion
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class Plot extends UnaryShowable {
  private static final int RESOLUTION = 40;
  private static final int FILL_ALPHA = 64;

  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain, PlotOption... options) {
    return new Plot(suo, domain, options);
  }

  // ---
  private final Path2D.Double path = new Path2D.Double();

  /** @param suo
   * @param domain may be null, in which case the plot is empty
   * @param whether area between function and axis is shaded */
  private Plot(ScalarUnaryOperator suo, Clip domain, PlotOption... options) {
    super(suo, domain, options);
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Optional<Clip> optional = x_clip(showableConfig);
    if (optional.isPresent()) {
      Clip x_clip = optional.orElseThrow();
      if (Sign.isPositive(x_clip.width()))
        render(showableConfig, graphics, x_clip);
    }
  }

  private void render(ShowableConfig showableConfig, Graphics2D graphics, Clip x_clip) {
    int segmentsPerPixel = 1;
    graphics.setColor(getColor());
    graphics.setStroke(getStroke());
    final double x0 = showableConfig.confX.x_pos(x_clip.min());
    final double x1 = showableConfig.confX.x_pos(x_clip.max());
    // TODO there is several interpolations concat here, also: enhance precision?
    // TODO BRIDGE values NaN, Inf are just skipped right now, see BrokenSUO
    path.reset();
    path.moveTo(x0, showableConfig.confY.x_pos(suo.apply(x_clip.min())));
    ScalarUnaryOperator interpX = LinearInterpolation.of(x_clip);
    final int size = (int) ((x1 - x0) * segmentsPerPixel);
    final double dx = 1.0 / segmentsPerPixel;
    double xc = x0;
    for (int i = 1; i <= size; ++i) {
      xc += dx;
      path.lineTo(xc, showableConfig.confY.x_pos(suo.apply(interpX.apply(Rational.of(i, size)))));
    }
    graphics.draw(path);
    if (isFilling()) {
      path.lineTo(x1, showableConfig.confY.x_pos(suo.apply(x_clip.max()).zero()));
      path.lineTo(x0, showableConfig.confY.x_pos(suo.apply(x_clip.min()).zero()));
      graphics.setColor(AwtUtil.withAlpha(getColor(), FILL_ALPHA));
      graphics.fill(path);
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Sign.isPositive(domain.width())) {
      Clip clip = StaticHelper.minMax(Subdivide.increasing(domain, RESOLUTION).maps(suo));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
