// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.Optional;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.tmp.TimeSeries;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Plot.html">Plot</a> */
public class Plot implements Showable {
  private static final int RESOLUTION = 20;
  // ---
  private final ScalarUnaryOperator suo;
  private final Clip x_domain;
  public Color color;
  public Stroke stroke;

  public Plot(ScalarUnaryOperator suo, Clip x_domain) {
    this.suo = suo;
    this.x_domain = x_domain;
  }

  public Plot(TimeSeries timeSeries) {
    this(scalar -> (Scalar) timeSeries.evaluate(scalar), timeSeries.domain());
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics _g) {
    Optional<Clip> optional = Clips.optionalIntersection(showableConfig.xRange, x_domain);
    if (optional.isPresent()) {
      int segmentsPerPixel = 1;
      Clip x_clip = optional.orElseThrow();
      // --
      Graphics2D graphics = (Graphics2D) _g.create();
      RenderQuality.setQuality(graphics);
      graphics.setColor(color);
      {
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
      graphics.dispose();
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBoundingBox.of( //
        x_domain, //
        StaticHelper.minMax(Subdivide.increasing(x_domain, RESOLUTION).map(suo))));
  }

  @Override
  public void setLabel(String string) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }
}
