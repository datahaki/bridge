// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.tmp.TimeSeries;

public class VisualSet extends VisualBase {
  private final List<VisualRow> visualRows = new ArrayList<>();
  private final ColorDataIndexed colorDataIndexed;

  public VisualSet(ColorDataIndexed colorDataIndexed) {
    this.colorDataIndexed = Objects.requireNonNull(colorDataIndexed);
  }

  /** uses Mathematica default color scheme */
  public VisualSet() {
    this(ColorDataLists._097.cyclic());
  }

  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}.
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public VisualRow add(Tensor points) {
    points.stream().forEach(row -> VectorQ.requireLength(row, 2));
    return _add(points);
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public VisualRow add(Tensor domain, Tensor values) {
    return add(Transpose.of(Tensors.of(domain, values)));
  }

  /** @param timeSeries
   * @param function mapping a {@link Tensor} value to a {@link Scalar} along the y-axis
   * @return */
  public VisualRow add(TimeSeries timeSeries, TensorScalarFunction function) {
    return _add(Tensor.of(timeSeries.stream() //
        .map(entry -> Tensors.of(entry.key(), function.apply(entry.value())))));
  }

  /** @param timeSeries with {@link Scalar} as values
   * @return */
  public VisualRow add(TimeSeries timeSeries) {
    return add(timeSeries, Scalar.class::cast);
  }

  private VisualRow _add(Tensor points) {
    if (Tensors.nonEmpty(points)) {
      if (!getAxisX().hasUnit())
        getAxisX().setUnit(QuantityUnit.of(points.Get(0, 0)));
      if (!getAxisY().hasUnit())
        getAxisY().setUnit(QuantityUnit.of(points.Get(0, 1)));
    }
    final int index = visualRows.size();
    VisualRow visualRow = new VisualRow(points, index);
    visualRow.setColor(colorDataIndexed.getColor(index));
    visualRows.add(visualRow);
    return visualRow;
  }

  public List<VisualRow> visualRows() {
    return Collections.unmodifiableList(visualRows);
  }

  public VisualRow getVisualRow(int index) {
    return visualRows.get(index);
  }

  public boolean hasLegend() {
    return visualRows.stream() //
        .map(VisualRow::getLabelString) //
        .anyMatch(Predicate.not(String::isEmpty));
  }
}
