// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.fig.BarLegend;
import ch.alpine.bridge.fig.BarLegendPlot;
import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.math.Meshgrid;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.chq.FiniteTensorQ;
import ch.alpine.tensor.col.ColorDataGradients;
import ch.alpine.tensor.col.ColorFormat;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.mat.Tolerance;
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

  private record Inner(Tensor xy, Tensor uv, Rescale rescale) {
  }

  private Inner compute(Meshgrid meshgrid) {
    Tensor px = meshgrid.px_incr();
    Tensor py = meshgrid.py_decr();
    int initialCapacity = px.length() * py.length();
    Tensor _uv = Tensors.reserve(initialCapacity);
    Tensor norms = Tensors.reserve(initialCapacity);
    Tensor xy = Tensor.of(py.stream() //
        .flatMap(y -> px.stream() //
            .map(x -> Unprotect.using(List.of(x, y)))) //
        .filter(p -> {
          Tensor v = tuo.apply(p);
          boolean isFinite = FiniteTensorQ.of(v);
          if (isFinite) {
            Scalar norm = Vector2Norm.of(v);
            if (Tolerance.CHOP.isZero(norm))
              return false;
            _uv.append(v);
            norms.append(norm);
            return true;
          }
          return false;
        }));
    Tensor uv;
    Rescale rescale;
    if (Tensors.isEmpty(xy)) {
      uv = Tensors.empty();
      rescale = new Rescale(Tensors.empty());
    } else {
      // TODO This does not account for dy !!!
      Scalar h = px.Get(1).subtract(px.Get(0));
      Scalar max = (Scalar) norms.stream().reduce(Max::of).orElseThrow();
      uv = _uv.multiply(h.divide(max.add(max)));
      rescale = new Rescale(norms);
    }
    inner_clip = rescale.clip();
    return new Inner(xy, uv, rescale);
  }

  private final TensorUnaryOperator tuo;
  private final ScalarTensorFunction colorDataGradient;
  private final Cache<Meshgrid, Inner> cache = Cache.of(this::compute, 1);
  // ---
  private Clip inner_clip = null;
  private int resolution = RESOLUTION_DEFAULT;

  private VectorPlot(TensorUnaryOperator tuo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    super(cbb);
    this.tuo = tuo;
    this.colorDataGradient = colorDataGradient;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public int getResolution() {
    return resolution;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    CoordinateBoundingBox cbb = set.contains(PlotOption.STRICT) //
        ? fullPlotRange().orElseThrow()
        : showableConfig.cbb();
    Dimension dimension = showableConfig.rectangle().getSize();
    dimension.width /= resolution;
    dimension.height /= resolution;
    Meshgrid meshgrid = Meshgrid.of(cbb, dimension);
    Inner inner = cache.apply(meshgrid);
    Tensor result = inner.rescale.result();
    int index = 0;
    for (Tensor p : inner.xy) {
      Tensor delta = inner.uv.get(index);
      ArrowPlot arrowPlot = new ArrowPlot(p.subtract(delta), p.add(delta));
      arrowPlot.setStroke(getStroke());
      arrowPlot.setColor(ColorFormat.toColor(colorDataGradient.apply(result.Get(index))));
      arrowPlot.render(showableConfig, graphics);
      ++index;
    }
  }

  @Override
  protected BarLegend barLegend() {
    return Objects.nonNull(inner_clip) //
        ? new BarLegend(inner_clip, colorDataGradient)
        : null;
  }
}
