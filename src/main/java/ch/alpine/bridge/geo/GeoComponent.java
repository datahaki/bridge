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

  public GeoComponent() {
    addMouseWheelListener(new MouseWheelListener() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
        Dimension dimension = getSize();
        Point center = AwtUtil.center(dimension);
        Point point = e.getPoint();
        int dx = point.x - center.x;
        int dy = point.y - center.y;
        tilePixel = tilePixel.shift(dx, dy).zoom(-e.getWheelRotation()).shift(-dx, -dy);
        repaint();
      }
    });
    MouseInputListener mouseInputListener = new MouseInputAdapter() {
      private Point down;

      @Override
      public void mousePressed(MouseEvent e) {
        down = e.getPoint();
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        Point here = e.getPoint();
        int dx = down.x - here.x;
        int dy = down.y - here.y;
        down = here;
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
    Dimension dimension = getSize();
    Point center = AwtUtil.center(dimension);
    TilePixel origin = tilePixel.shift(-center.x, -center.y);
    MapImagesCache mapImagesCache = getCache();
    for (int ix = 0; ix < dimension.width + 256; ix += 256)
      for (int iy = 0; iy < dimension.height + 256; iy += 256) {
        TilePixel shift = origin.shift(ix, iy);
        BufferedImage bufferedImage = mapImagesCache.getTile(shift.tile());
        graphics.drawImage(bufferedImage, ix - shift.pix(), iy - shift.piy(), null);
      }
    renderMore(graphics);
  }

  /** override if necessary
   * 
   * @param graphics */
  public void renderMore(Graphics2D graphics) {
    // ---
  }
}
