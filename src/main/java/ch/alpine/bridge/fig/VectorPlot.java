// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorPlot.html">VectorPlot</a> */
public class VectorPlot extends BaseShowable {
  private static final int RESOLUTION_DEFAULT = 30;

  public static VectorPlot of(TensorUnaryOperator tuo, CoordinateBoundingBox cbb) {
    return of(tuo, cbb, ColorDataGradients.DENSITY);
  }

  public static VectorPlot of(TensorUnaryOperator tuo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return new VectorPlot(tuo, cbb, colorDataGradient);
  }

  private final TensorUnaryOperator tuo;
  private final CoordinateBoundingBox cbb;
  private final ScalarTensorFunction colorDataGradient;
  private final Cache<CoordinateBoundingBox, Inner> cache = Cache.of(this::recompute, 1);
  // ---
  private Clip inner_clip = null;
  private int resolution = RESOLUTION_DEFAULT;

  private class Inner {
    private final Tensor xy;
    private final Tensor uv;
    private final Rescale rescale;

    public Inner(CoordinateBoundingBox cbb, int resolution) {
      // TODO BRIDGE resolution based on aspect ratio and cbb ?
      Tensor dx = Subdivide.intermediate_increasing(cbb.clip(0), resolution);
      Tensor dy = Subdivide.intermediate_decreasing(cbb.clip(1), resolution);
      xy = Tensor.of(dy.stream().parallel() //
          .flatMap(y -> dx.stream().map(x -> Tensors.of(x, y))));
      Tensor _uv = tuo.slash(xy);
      Tensor norms = Tensor.of(_uv.stream().map(Vector2Norm::of));
      Scalar h = dx.Get(1).subtract(dx.Get(0));
      Scalar max = (Scalar) norms.stream().reduce(Max::of).orElseThrow();
      uv = _uv.multiply(h.multiply(RealScalar.of(0.5)).divide(max));
      rescale = new Rescale(norms);
      inner_clip = rescale.clip();
    }
  }

  private VectorPlot(TensorUnaryOperator tuo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    this.tuo = tuo;
    this.cbb = cbb;
    this.colorDataGradient = colorDataGradient;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    graphics.setStroke(getStroke());
    CoordinateBoundingBox cbb = showableConfig.getCbb();
    Inner inner = cache.apply(cbb);
    Tensor result = inner.rescale.result();
    int index = 0;
    for (Tensor p : inner.xy) {
      Tensor delta = inner.uv.get(index);
      Point2D p0 = showableConfig.toPoint2D(p);
      Point2D p1 = showableConfig.toPoint2D(p.add(delta));
      graphics.setColor(ColorFormat.toColor(colorDataGradient.apply(result.Get(index))));
      Path2D.Double path = new Path2D.Double();
      path.moveTo(p0.getX(), p0.getY());
      path.lineTo(p1.getX(), p1.getY());
      graphics.draw(path);
      ++index;
    }
  }

  private Inner recompute(CoordinateBoundingBox cbb) {
    return new Inner(cbb, resolution);
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }
}
