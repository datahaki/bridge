// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ch.alpine.bridge.geo.MapImagesCache;
import ch.alpine.bridge.geo.Tile;
import ch.alpine.bridge.geo.TileImage;
import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.geo.TileServer;
import ch.alpine.bridge.geo.TileServers;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class GeoGraphics extends BaseShowable {
  public static Showable create() {
    return new GeoGraphics();
  }

  public TileServer tileServer = TileServers.OpenStreetMap;
  private MapImagesCache mapImagesCache;

  private GeoGraphics() {
    mapImagesCache = tileServer.cache();
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    TilePixel origin = TilePixel.geo(showableConfig.rectangle(), showableConfig.cbb());
    Rectangle rectangle = showableConfig.rectangle();
    Dimension dimension = rectangle.getSize();
    int wid = origin.wid();
    // Shape shape = graphics.getClip();
    // Rectangle rectangle = showableConfig.rectangle();
    // graphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    // check if all available
    Set<Tile> missing = new HashSet<>();
    if (0 < origin.tile().z())
      for (int pix = -origin.pix(); pix < dimension.width; pix += wid)
        for (int piy = -origin.piy(); piy < dimension.height; piy += wid) {
          TilePixel shift = origin.shift(pix, piy);
          TileImage tileImage = mapImagesCache.getTile(shift.tile());
          if (Objects.isNull(tileImage))
            missing.add(shift.tile().parent());
        }
    {
      Optional<TilePixel> optional = origin.widIncr(-256, tileServer.z_max());
      if (optional.isPresent()) {
        TilePixel coarse = optional.orElseThrow();
        // IO.println(coarse.pix());
        for (int ix = -coarse.pix(); ix < dimension.width / 2; ix += wid)
          for (int iy = -coarse.piy(); iy < dimension.height / 2; iy += wid) {
            TilePixel shift = coarse.shift(ix, iy);
            Tile tile = shift.tile();
            if (missing.contains(tile)) {
              TileImage tileImage = mapImagesCache.getTile(tile);
              if (Objects.nonNull(tileImage))
                graphics.drawImage(tileImage.bufferedImage(), ix * 2, iy * 2, wid * 2, wid * 2, null);
            }
          }
      }
    }
    // graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
    // TextContour textContour = TextContour.of(graphics);
    for (int pix = -origin.pix(); pix < dimension.width; pix += wid)
      for (int piy = -origin.piy(); piy < dimension.height; piy += wid) {
        Tile tile = origin.shift(pix, piy).tile();
        TileImage tileImage = mapImagesCache.getTile(tile);
        if (Objects.nonNull(tileImage)) {
          graphics.drawImage(tileImage.bufferedImage(), pix, piy, wid, wid, null);
          // if (pngSize)
          // textContour.draw(tile + " " + tileImage.length(), pix, piy);
        }
      }
    if (!missing.isEmpty()) {
      graphics.setColor(new Color(255, 0, 0, 128 + 64));
      graphics.drawRect(1, 1, dimension.width - 3, dimension.height - 3);
    }
  }

  @Override
  public void tender(ShowableConfig showableConfig, Graphics2D graphics) {
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.empty();
  }

  @Override
  public void setAlpha(int alpha) {
  }
}
