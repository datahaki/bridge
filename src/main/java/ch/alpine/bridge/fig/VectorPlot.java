// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.chq.FiniteTensorQ;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorPlot.html">VectorPlot</a> */
public class VectorPlot extends BarLegendPlot {
  private static final int RESOLUTION_DEFAULT = 30;

  public static VectorPlot of(TensorUnaryOperator tuo, CoordinateBoundingBox cbb) {
    return of(tuo, cbb, ColorDataGradients.EMBER);
  }

  public static VectorPlot of(TensorUnaryOperator tuo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return new VectorPlot(tuo, cbb, colorDataGradient);
  }

  private final TensorUnaryOperator tuo;
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
      int initialCapacity = dx.length() * dy.length();
      Tensor _uv = Tensors.reserve(initialCapacity);
      xy = Tensor.of(dy.stream() //
          .flatMap(y -> dx.stream().map(x -> Unprotect.using(List.of(x, y)))) //
          .filter(p -> {
            Tensor v = tuo.apply(p);
            boolean isFinite = FiniteTensorQ.of(v);
            if (isFinite)
              _uv.append(v);
            return isFinite;
          }));
      Tensor norms = Tensor.of(_uv.stream().map(Vector2Norm::of));
      Scalar h = dx.Get(1).subtract(dx.Get(0)); // TODO This does not account for dy !!!
      Scalar max = (Scalar) norms.stream().reduce(Max::of).orElseThrow();
      uv = _uv.multiply(h.divide(max.add(max)));
      rescale = new Rescale(norms);
      inner_clip = rescale.clip();
      Integers.requireEquals(xy.length(), rescale.result().length());
    }
  }

  private VectorPlot(TensorUnaryOperator tuo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    super(cbb);
    this.tuo = tuo;
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
      ArrowPlot arrowPlot = new ArrowPlot(p.subtract(delta), p.add(delta));
      Color color = ColorFormat.toColor(colorDataGradient.apply(result.Get(index)));
      arrowPlot.setColor(color);
      arrowPlot.render(showableConfig, graphics);
      ++index;
    }
  }

  private Inner recompute(CoordinateBoundingBox cbb) {
    return new Inner(cbb, resolution);
  }

  @Override
  protected BarLegend barLegend() {
    return Objects.nonNull(inner_clip) //
        ? new BarLegend(inner_clip, colorDataGradient)
        : null;
  }
}
