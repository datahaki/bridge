// code by jph
package ch.alpine.bridge.fig.plt;

import java.util.Optional;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

abstract class BasePointsPlot extends BaseShowable {
  protected final Tensor points;

  protected BasePointsPlot(Tensor points) {
    points.forEach(row -> VectorQ.requireLength(row, 2));
    this.points = points.unmodifiable();
  }

  @Override // from Showable
  public final Optional<CoordinateBoundingBox> fullPlotRange() {
    return Tensors.isEmpty(points) //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            StaticHelper.minMax(points.get(Tensor.ALL, 0)), //
            StaticHelper.minMax(points.get(Tensor.ALL, 1))));
  }
}
