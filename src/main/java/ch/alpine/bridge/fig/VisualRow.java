// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.chq.ScalarQ;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;

public class VisualRow {
  private static final Stroke STROKE_DEFAULT = new BasicStroke(1f);
  // ---
  private final Tensor points;
  private String label;
  private Color color = Color.BLUE;
  private boolean joined = false;
  /** not serializable */
  private transient Stroke stroke;

  /** Mathematica::ListPlot[points]
   * 
   * @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}.
   * The special case when points == {} is also allowed.
   * @param index */
  /* package */ VisualRow(Tensor points, int index) {
    ScalarQ.thenThrow(points);
    this.points = points;
  }

  /** @return points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}} */
  public Tensor points() {
    return points.unmodifiable();
  }

  Optional<Clip> pointsClip(int index) {
    return Optional.ofNullable(points.stream() //
        .map(xy -> xy.Get(index)) //
        .collect(MinMax.toClip()));
  }

  // ---
  /** @param color */
  public void setColor(Color color) {
    this.color = Objects.requireNonNull(color);
  }

  /** @return */
  public Color getColor() {
    return color;
  }

  // ---
  /** @param stroke */
  public void setStroke(Stroke stroke) {
    this.stroke = Objects.requireNonNull(stroke);
  }

  /** @return */
  public Stroke getStroke() {
    return Objects.isNull(stroke) //
        ? STROKE_DEFAULT
        : stroke;
  }

  // ---
  /** @param string */
  public void setLabel(String string) {
    label = string;
  }

  /** @return */
  public String getLabelString() {
    return label;
  }

  // ---
  /** @param autoSort */
  public VisualRow setJoined(boolean joined) {
    this.joined = joined;
    return this;
  }

  /** @return */
  public boolean getJoined() {
    return joined;
  }
}
