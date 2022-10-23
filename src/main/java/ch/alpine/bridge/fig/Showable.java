// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Optional;

import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public interface Showable {
  void render(ShowableConfig showableConfig, Graphics _g);
  
  Optional<CoordinateBoundingBox> fullPlotRange();

  void setLabel(String string);

  void setColor(Color color);
}
