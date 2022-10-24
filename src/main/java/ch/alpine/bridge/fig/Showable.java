// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Optional;

import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public interface Showable {
  /** @param showableConfig
   * @param graphics */
  void render(ShowableConfig showableConfig, Graphics graphics);

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
}
