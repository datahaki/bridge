// code by jph
package ch.alpine.bridge.geo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Optional;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.fig.BackgroundPlotMarker;
import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;

class GeoGraphics extends BaseShowable implements BackgroundPlotMarker {
  public static Showable of(TilePixel tilePixel) {
    return new GeoGraphics(tilePixel);
  }

  private final TilePixel tilePixel;

  private GeoGraphics(TilePixel tilePixel) {
    this.tilePixel = tilePixel;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    Dimension dimension = rectangle.getSize();
    Point center = AwtUtil.center(dimension);
    TilePixel origin = tilePixel.shift(-center.x, -center.y);
    MapImagesCache mapImagesCache = TileServers.OpenStreetMap.createCache();
    for (int ix = 0; ix < dimension.width + 256; ix += 256)
      for (int iy = 0; iy < dimension.height + 256; iy += 256) {
        TilePixel shift = origin.shift(ix, iy);
        graphics.drawImage(mapImagesCache.getTile(shift.tile()), ix - shift.pix(), iy - shift.piy(), null);
      }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBoundingBox.of(Clips.unit(), Clips.unit()));
  }
}
