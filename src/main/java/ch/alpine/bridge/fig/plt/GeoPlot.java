// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.util.Optional;

import ch.alpine.bridge.fig.BackgroundPlotMarker;
import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.geo.Tile;
import ch.alpine.bridge.geo.TileImage;
import ch.alpine.bridge.geo.TileServer;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class GeoPlot extends BaseShowable implements BackgroundPlotMarker, Showable {
  public static Showable of(TileServer tileServer) {
    return new GeoPlot(tileServer);
  }

  // ---
  private final TileServer tileServer;

  private GeoPlot(TileServer tileServer) {
    this.tileServer = tileServer;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    TileImage tileImage = tileServer.cache().getTile(new Tile(0, 0, 0));
    graphics.drawImage(tileImage.bufferedImage(), 0, 0, null);
  }

  @Override
  public void tender(ShowableConfig showableConfig, Graphics2D graphics) {
    // render nothing
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.empty();
  }

  @Override
  public void setAlpha(int alpha) {
    // ignored
  }
}
