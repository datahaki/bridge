// code by jph
package ch.alpine.bridge.geo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import ch.alpine.bridge.awt.AwtUtil;

/** Careful: the field tilePixel has to be initialized */
public class GeoComponent extends JComponent {
  private final Map<TileServer, MapImagesCache> cache = new IdentityHashMap<>();
  /** center */
  public TilePixel tilePixel;
  public TileServer tileServer = TileServers.OpenStreetMap;

  public TilePixel fromPoint(Point point) {
    Dimension dimension = getSize();
    Point center = AwtUtil.center(dimension);
    int dx = point.x - center.x;
    int dy = point.y - center.y;
    return tilePixel.shift(dx, dy);
  }

  public GeoComponent() {
    addMouseWheelListener(new MouseWheelListener() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent event) {
        Dimension dimension = getSize();
        Point center = AwtUtil.center(dimension);
        Point point = event.getPoint();
        int dx = point.x - center.x;
        int dy = point.y - center.y;
        TilePixel s1 = tilePixel.shift(dx, dy);
        Optional<TilePixel> optional = s1.zoomIncr(-event.getWheelRotation(), tileServer.z_max());
        if (optional.isPresent()) {
          tilePixel = optional.orElseThrow().shift(-dx, -dy);
          repaint();
        }
      }
    });
    MouseInputListener mouseInputListener = new MouseInputAdapter() {
      private Point down;

      @Override
      public void mousePressed(MouseEvent e) {
        down = e.getPoint();
      }

      @Override
      public void mouseDragged(MouseEvent event) {
        Point point = event.getPoint();
        int dx = down.x - point.x;
        int dy = down.y - point.y;
        down = point;
        int f = tilePixel.tile().z() / 3 + 1;
        tilePixel = tilePixel.shift(dx * f, dy * f);
        repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        down = null;
      }
    };
    addMouseListener(mouseInputListener);
    addMouseMotionListener(mouseInputListener);
  }

  public MapImagesCache getCache() {
    return cache.computeIfAbsent(tileServer, TileServer::cache);
  }

  @Override
  protected final void paintComponent(Graphics _g) {
    Graphics2D graphics = (Graphics2D) _g;
    MapImagesCache mapImagesCache = getCache();
    Dimension dimension = getSize();
    TilePixel origin = fromPoint(new Point(0, 0));
    boolean missing = false;
    {
      for (int pix = -origin.pix(); pix < dimension.width; pix += 256)
        for (int piy = -origin.piy(); piy < dimension.height; piy += 256) {
          BufferedImage bufferedImage = mapImagesCache.getTile(origin.shift(pix, piy).tile());
          missing |= Objects.isNull(bufferedImage);
        }
    }
    if (missing) {
      // IO.println("missing");
      Optional<TilePixel> optional = origin.zoomIncr(-1, 20);
      if (optional.isPresent()) {
        TilePixel coarse = optional.orElseThrow();
        for (int ix = -coarse.pix(); ix < dimension.width / 2; ix += 256)
          for (int iy = -coarse.piy(); iy < dimension.height / 2; iy += 256) {
            TilePixel shift = coarse.shift(ix, iy);
            BufferedImage bufferedImage = mapImagesCache.getTile(shift.tile());
            graphics.drawImage(bufferedImage, ix * 2, iy * 2, 512, 512, null);
          }
      }
    }
    {
      for (int pix = -origin.pix(); pix < dimension.width; pix += 256)
        for (int piy = -origin.piy(); piy < dimension.height; piy += 256) {
          BufferedImage bufferedImage = mapImagesCache.getTile(origin.shift(pix, piy).tile());
          graphics.drawImage(bufferedImage, pix, piy, null);
        }
    }
    GeoLayer geoLayer = new GeoLayer(origin);
    renderMore(geoLayer, graphics);
  }

  /** override if necessary
   * 
   * @param graphics */
  public void renderMore(GeoLayer geoLayer, Graphics2D graphics) {
    // ---
  }
}
