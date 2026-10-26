// code by jph
package ch.alpine.bridge.geo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.fig.Ticks;
import ch.alpine.bridge.gfx.TextContour;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** Careful: the field tilePixel has to be initialized */
public class GeoComponent extends JComponent {
  private final Map<TileServer, MapImagesCache> cache = new IdentityHashMap<>();
  /** center */
  public TilePixel tilePixel;
  public TileServer tileServer = TileServers.OpenStreetMap;
  public Boolean crosshair = true;
  public Boolean ticks = true;
  public Boolean gridlines = true;
  public Boolean pngSize = true;
  public Color gridLineColor = new Color(128, 128, 128, 64);

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
          TileImage tileImage = mapImagesCache.getTile(origin.shift(pix, piy).tile());
          missing |= Objects.isNull(tileImage);
        }
    }
    if (missing) {
      Optional<TilePixel> optional = origin.zoomIncr(-1, 20);
      if (optional.isPresent()) {
        TilePixel coarse = optional.orElseThrow();
        for (int ix = -coarse.pix(); ix < dimension.width / 2; ix += 256)
          for (int iy = -coarse.piy(); iy < dimension.height / 2; iy += 256) {
            TilePixel shift = coarse.shift(ix, iy);
            TileImage tileImage = mapImagesCache.getTile(shift.tile());
            if (Objects.nonNull(tileImage))
              graphics.drawImage(tileImage.bufferedImage(), ix * 2, iy * 2, 512, 512, null);
          }
      }
    }
    {
      graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
      RenderQuality.setQuality(graphics);
      TextContour textContour = TextContour.of(graphics);
      for (int pix = -origin.pix(); pix < dimension.width; pix += 256)
        for (int piy = -origin.piy(); piy < dimension.height; piy += 256) {
          Tile tile = origin.shift(pix, piy).tile();
          TileImage tileImage = mapImagesCache.getTile(tile);
          if (Objects.nonNull(tileImage)) {
            graphics.drawImage(tileImage.bufferedImage(), pix, piy, null);
            if (pngSize)
              textContour.draw(tile + " " + tileImage.length(), 128 + pix, 128 + piy);
          }
        }
    }
    if (missing) {
      graphics.setColor(new Color(255, 0, 0, 128 + 64));
      graphics.drawRect(1, 1, dimension.width - 3, dimension.height - 3);
    }
    RenderQuality.setQuality(graphics);
    RenderQuality.smoothLine(graphics, false);
    graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
    TextContour textContour = TextContour.of(graphics);
    final Point center = AwtUtil.center(dimension);
    GeoLayer geoLayer = new GeoLayer(origin);
    if (ticks && 2 < tilePixel.tile().z()) {
      ScalarUnaryOperator suo = UnitConvert.SI().to(Degree.UNIT);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      { // lon along "X - axis"
        Scalar min = suo.apply(tilePixel.shift(-center.x, 0).lat_lon().Get(1));
        Scalar max = suo.apply(tilePixel.shift(+center.x, 0).lat_lon().Get(1));
        while (Scalars.lessThan(max, min))
          max = max.add(Quantity.of(360, "deg"));
        Clip clip = Clips.interval(min, max);
        if (Scalars.nonZero(clip.length())) {
          List<Scalar> list = Ticks.stream(clip, Rational.of(100, dimension.width)).toList();
          Tensor lat_lon = tilePixel.lat_lon().maps(suo);
          int height = fontMetrics.getDescent();
          for (Scalar tick : list) {
            lat_lon.set(tick, 1);
            Point point = geoLayer.toPoint(lat_lon);
            int x = point.x;
            if (gridlines) {
              graphics.setColor(gridLineColor);
              graphics.drawLine(x, 0, x, dimension.height);
            }
            int y = dimension.height - 20;
            graphics.setStroke(new BasicStroke()); // thickness of outline
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x, y - 10, x, dimension.height);
            textContour.draw(" " + Ticks.format(tick), x, dimension.height - height - 2);
          }
        }
      }
      { // lat along "Y - axis"
        Scalar max = suo.apply(tilePixel.shift(0, -center.y).lat_lon().Get(0));
        Scalar min = suo.apply(tilePixel.shift(0, +center.y).lat_lon().Get(0));
        while (Scalars.lessThan(max, min))
          max = max.add(Quantity.of(180, "deg"));
        Clip clip = Clips.interval(min, max);
        List<Scalar> list = Ticks.stream(clip, Rational.of(100, dimension.height)).toList();
        Tensor lat_lon = tilePixel.lat_lon().maps(suo);
        // establish longest string, e.g. "47.15"
        OptionalInt optionalInt = list.stream().map(Ticks::format).mapToInt(fontMetrics::stringWidth).max();
        if (optionalInt.isPresent()) {
          int width = optionalInt.getAsInt() + 5;
          for (Scalar tick : list) {
            lat_lon.set(tick, 0);
            Point point = geoLayer.toPoint(lat_lon);
            int x = dimension.width - width;
            int y = point.y;
            if (gridlines) {
              graphics.setColor(gridLineColor);
              lat_lon.set(tick, 0);
              graphics.drawLine(0, point.y, dimension.width, point.y);
            }
            graphics.setStroke(new BasicStroke()); // thickness of outline
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x, y, dimension.width, y);
            String string = Ticks.format(tick);
            textContour.draw(string, dimension.width - width, y - 2);
          }
        }
      }
    }
    /* upper left corner */
    if (crosshair) { // draw crosshair
      graphics.setStroke(new BasicStroke());
      graphics.setColor(new Color(255, 0, 0, 192));
      int r = 3;
      graphics.drawLine(center.x - r, center.y, center.x + r, center.y);
      graphics.drawLine(center.x, center.y - r, center.x, center.y + r);
      textContour.draw("z=" + tilePixel.tile().z(), 2, 20);
    }
    renderMore(geoLayer, graphics);
  }

  /** override if necessary
   * 
   * @param graphics */
  public void renderMore(GeoLayer geoLayer, Graphics2D graphics) {
    // ---
  }
}
