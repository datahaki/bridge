// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.util.Optional;

import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscretePlot.html">DiscretePlot</a> */
public class DiscretePlot extends BaseShowable {
  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    // TODO Auto-generated method stub
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    // TODO Auto-generated method stub
    return Optional.empty();
  }
}
