// code by gjoel, jph
package ch.alpine.java.fig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.ChartFactory;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.qty.Unit;

public class VisualSet implements Serializable {
  static {
    // design is bad but instigated by the jfree library
    ChartFactory.setChartTheme(DefaultChartTheme.STANDARD);
    // BarRenderer.setDefaultBarPainter(new StandardBarPainter());
    // BarRenderer.setDefaultShadowsVisible(false);
  }
  // ---
  private final List<VisualRow> visualRows = new ArrayList<>();
  private final ColorDataIndexed colorDataIndexed;
  private String plotLabel = "";
  private String axesLabelX = "";
  private String axesLabelY = "";

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
    final int index = visualRows.size();
    points.stream().forEach(row -> VectorQ.requireLength(row, 2));
    VisualRow visualRow = new VisualRow(points, index);
    visualRow.setColor(colorDataIndexed.getColor(index));
    visualRows.add(visualRow);
    return visualRow;
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public VisualRow add(Tensor domain, Tensor values) {
    return add(Transpose.of(Tensors.of(domain, values)));
  }

  public List<VisualRow> visualRows() {
    return Collections.unmodifiableList(visualRows);
  }

  public VisualRow getVisualRow(int index) {
    return visualRows.get(index);
  }

  /** @param string to appear above plot */
  public void setPlotLabel(String string) {
    plotLabel = string;
  }

  /** @return */
  public String getPlotLabel() {
    return plotLabel;
  }

  /** @param string label of x-axis */
  public void setAxesLabelX(String string) {
    axesLabelX = string;
  }

  /** @return label of x-axis */
  public String getAxesLabelX() {
    return (axesLabelX + " " + getUnitXString()).strip();
  }

  /** @param string label of y-axis */
  public void setAxesLabelY(String string) {
    axesLabelY = string;
  }

  /** @return label of y-axis */
  public String getAxesLabelY() {
    return (axesLabelY + " " + getUnitYString()).strip();
  }

  public boolean hasLegend() {
    return visualRows.stream() //
        .map(VisualRow::getLabelString) //
        .anyMatch(string -> !string.isEmpty());
  }

  /***************************************************/
  private Unit unitX = Unit.ONE;
  private Unit unitY = Unit.ONE;

  /* package */ void setUnitX(Unit unit) {
    this.unitX = unit;
  }

  /* package */ void setUnitY(Unit unit) {
    this.unitY = unit;
  }

  /* package */ String getUnitXString() {
    return unitX.equals(Unit.ONE) ? "" : "[" + unitX + "]";
  }

  /* package */ String getUnitYString() {
    return unitY.equals(Unit.ONE) ? "" : "[" + unitY + "]";
  }
}
