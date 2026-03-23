// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.util.Optional;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

enum EmptyPlot {
  ;
  public static final Showable INSTANCE = new BaseShowable() {
    @Override
    public void render(ShowableConfig showableConfig, Graphics2D graphics) {
      // ---
    }

    @Override
    public Optional<CoordinateBoundingBox> fullPlotRange() {
      return Optional.empty();
    }
  };
}
