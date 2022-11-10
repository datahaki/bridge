// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Optional;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public interface Showable {
  /** @param showableConfig
   * @param graphics with high quality and clip to plot area
   * @see RenderQuality */
  void render(ShowableConfig showableConfig, Graphics2D graphics);

  /** @param showableConfig
   * @param _g */
  void tender(ShowableConfig showableConfig, Graphics _g);

  /** @return */
  Optional<CoordinateBoundingBox> fullPlotRange();

  /** @param string */
  void setLabel(String string);

  /** @return */
  String getLabel();

  /** @param color */
  void setColor(Color color);

  /** @return */
  Color getColor();

  /** @param stroke */
  void setStroke(Stroke stroke);

  /** @return */
  Stroke getStroke();

  /** Mathematica: "DataReversed"
   * 
   * @return whether y-axis should be flipped */
  default boolean flipYAxis() {
    return false;
  }

  /** @return */
  default Optional<Scalar> aspectRatioHint() {
    return Optional.empty();
  }
}
