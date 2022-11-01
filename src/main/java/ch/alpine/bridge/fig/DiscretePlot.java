// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscretePlot.html">DiscretePlot</a> */
public class DiscretePlot extends BaseShowable {
  /** @param suo
   * @param domain
   * @return */
  public static Showable of(ScalarUnaryOperator suo, Clip domain) {
    return new DiscretePlot(suo, domain);
  }

  // ---
  private final ScalarUnaryOperator suo;
  private final Clip domain;

  // ---
  /** @param suo
   * @param domain may be null, in which case the plot is empty */
  private DiscretePlot(ScalarUnaryOperator suo, Clip domain) {
    this.suo = suo;
    this.domain = domain;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Tensor samples = Range.of( //
        Scalars.bigIntegerValueExact(domain.min()), //
        Scalars.bigIntegerValueExact(domain.max()));
    Color color = StaticHelper.withAlpha(getColor(), 64);
    double radius = 2.5;
    graphics.setStroke(getStroke());
    for (Tensor _x : samples) {
      Scalar x = (Scalar) _x;
      Scalar y = suo.apply(x);
      double x_pos = showableConfig.x_pos(x);
      double y0 = showableConfig.y_pos(y.zero());
      double y1 = showableConfig.y_pos(y);
      graphics.setColor(getColor());
      graphics.fill(new Ellipse2D.Double(x_pos - radius, y1 - radius, 2 * radius, 2 * radius));
      graphics.setColor(color);
      graphics.draw(new Line2D.Double(x_pos, y0, x_pos, y1));
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Objects.nonNull(domain) && Sign.isPositive(domain.width())) {
      Tensor samples = Range.of( //
          Scalars.bigIntegerValueExact(domain.min()), //
          Scalars.bigIntegerValueExact(domain.max()));
      Clip clip = StaticHelper.minMax(samples.map(suo));
      if (Objects.nonNull(clip))
        return Optional.of(CoordinateBoundingBox.of(domain, clip));
    }
    return Optional.empty();
  }
}
