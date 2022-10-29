// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.util.Optional;

import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/BoxWhiskerChart.html">BoxWhiskerChart</a> */
public class BoxWhiskerChart extends BaseShowable {
  // TODO BRIDGE IMPL
  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    // ---
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.empty();
  }
}
