// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ParametricPlot.html">ParametricPlot</a> */
public class ParametricPlot extends BaseShowable {
  private static final int RESOLUTION = 20;

  public static Showable of(ScalarTensorFunction stf, Clip domain) {
    return new ParametricPlot(stf, domain);
  }

  // ---
  private final ScalarTensorFunction stf;
  private final Clip domain;

  private ParametricPlot(ScalarTensorFunction stf, Clip domain) {
    this.stf = stf;
    this.domain = domain;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (Sign.isPositive(domain.width())) {
      graphics.setColor(getColor());
      graphics.setStroke(getStroke());
      Path2D.Double path = new Path2D.Double();
      // TODO BRIDGE adaptive resolution
      Tensor points = Subdivide.increasing(domain, 3 * RESOLUTION).map(stf);
      {
        Point2D point2d = showableConfig.toPoint2D(points.get(0));
        path.moveTo(point2d.getX(), point2d.getY());
      }
      points.stream().skip(1).forEach(row -> {
        Point2D point2d = showableConfig.toPoint2D(row);
        path.lineTo(point2d.getX(), point2d.getY());
      });
      graphics.draw(path);
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    if (Objects.nonNull(domain) && Sign.isPositive(domain.width()))
      return Optional.of(CoordinateBounds.of(Subdivide.increasing(domain, RESOLUTION).map(stf)));
    return Optional.empty();
  }
}
