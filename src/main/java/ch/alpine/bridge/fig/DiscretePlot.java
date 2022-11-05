// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
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
    Tensor samples = Range.closed(domain);
    Color color = StaticHelper.withAlpha(getColor(), 64);
    double radius = 2.5;
    graphics.setStroke(getStroke());
    for (Tensor _x : samples) {
      Scalar x = (Scalar) _x;
      Scalar y = suo.apply(x);
      Point2D point2d = showableConfig.toPoint2D(Tensors.of(x, y));
      double y0 = showableConfig.y_pos(y.zero());
      graphics.setColor(color);
      graphics.draw(new Line2D.Double(point2d.getX(), y0, point2d.getX(), point2d.getY()));
      graphics.setColor(getColor());
      graphics.fill(new Ellipse2D.Double(point2d.getX() - radius, point2d.getY() - radius, 2 * radius, 2 * radius));
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
