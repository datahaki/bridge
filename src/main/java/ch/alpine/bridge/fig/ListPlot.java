// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** Hint:
 * to render list plot in a custom graphics use
 * jFreeChart.draw(graphics2d, rectangle2D)
 * 
 * to export to a graphics file use
 * ChartUtils.saveChartAsPNG(file, jFreeChart, width, height);
 * 
 * to embed figure as separate panel in gui use {@link ChartPanel}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public class ListPlot implements Showable {
  /** Remark:
   * We would like to make joined property of VisualRow, but JFreeChart does not support
   * this granularity.
   * 
   * Tested with up to 10 million points - a little slow but possible.
   * 
   * @param visualSet
   * @param joined for lines between coordinates, otherwise scattered points
   * @return */
  public static Show of(Tensor points) {
    Show show = new Show();
    show.add(new ListPlot(points));
    return show;
  }

  // ---
  private final Tensor points;

  private ListPlot(Tensor points) {
    this.points = points;
  }

  public ListPlot(Tensor domain, Tensor tensor) {
    this(Transpose.of(Tensors.of(domain, tensor)));
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics _g) {
    if (0 < points.length()) {
      Graphics2D graphics = (Graphics2D) _g.create();
      RenderQuality.setQuality(graphics);
      graphics.setColor(color);
      Path2D.Double path = new Path2D.Double();
      {
        Point2D.Double point2d = showableConfig.toPoint2D(points.get(0));
        path.moveTo(point2d.x, point2d.y);
      }
      points.stream().skip(1).forEach(row -> {
        Point2D.Double point2d = showableConfig.toPoint2D(row);
        path.lineTo(point2d.x, point2d.y);
      });
      graphics.draw(path);
      graphics.dispose();
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Tensors.isEmpty(points) //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            StaticHelper.minMax(points.get(Tensor.ALL, 0)), //
            StaticHelper.minMax(points.get(Tensor.ALL, 1))));
  }

  @Override
  public void setLabel(String string) {
    // TODO Auto-generated method stub
  }

  Color color;

  @Override
  public void setColor(Color color) {
    this.color = color;
  }
}
