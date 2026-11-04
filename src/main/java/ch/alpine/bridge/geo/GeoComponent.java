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
import java.awt.Rectangle;
import java.awt.Shape;
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
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.fig.Ticks;
import ch.alpine.bridge.gfx.TextContour;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;
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
        Optional<TilePixel> optional = s1.widIncr(-64 * event.getWheelRotation(), tileServer.z_max());
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
  // ShowableConfig getShowableConfig() {
  // FontMetrics fontMetrics = graphics.getFontMetrics();
  // Rectangle rectangle = Show.optionalDefaultInsets(dimension, fontMetrics).orElseThrow();
  // Point p00 = rectangle.getLocation();
  // Point p11 = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
  // TilePixel tp00 = origin.shift(p00.x, p00.y);
  // TilePixel tp11 = origin.shift(p11.x, p11.y);
  // Tensor lon_lat0 = tp00.lon_lat();
  // Tensor lon_lat1 = tp11.lon_lat();
  // ScalarUnaryOperator suo = UnitConvert.SI().to(Degree.UNIT);
  // CoordinateBoundingBox cbb = CoordinateBounds.of(Tensors.of(lon_lat0, lon_lat1).maps(suo));
  // // the choice of yDecr is mostly correct but not entirely general
  // ShowableConfig.geo(rectangle, cbb, origin);
  // }

  private ShowableConfig createSC(Graphics _g) {
    Dimension dimension = getSize();
    Rectangle rectangle = new Rectangle(new Point(0, 0), dimension);
    TilePixel origin = fromPoint(new Point(0, 0));
    if (2 < origin.tile().z()) {
      Point p00 = rectangle.getLocation();
      Point p11 = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
      TilePixel tp00 = origin.shift(p00.x, p00.y);
      TilePixel tp11 = origin.shift(p11.x, p11.y);
      Tensor lon_lat0 = tp00.lon_lat();
      Tensor lon_lat1 = tp11.lon_lat();
      ScalarUnaryOperator suo = UnitConvert.SI().to(Degree.UNIT);
      CoordinateBoundingBox cbb = CoordinateBounds.of(Tensors.of(lon_lat0, lon_lat1).maps(suo));
      // the choice of yDecr is mostly correct but not entirely general
      return ShowableConfig.geo(rectangle, cbb, origin);
    }
    return ShowableConfig.empty(rectangle);
  }

  @Override
  protected final void paintComponent(Graphics _g) {
    Graphics2D graphics = (Graphics2D) _g;
    RenderQuality.setQuality(graphics);
    MapImagesCache mapImagesCache = getCache();
    Dimension dimension = getSize();
    TilePixel origin = fromPoint(new Point(0, 0));
    boolean missing = false;
    final int wid = tilePixel.wid();
    final ShowableConfig showableConfig = createSC(_g);
    {
      Shape shape = graphics.getClip();
      Rectangle rectangle = showableConfig.rectangle();
      graphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      {
        for (int pix = -origin.pix(); pix < dimension.width; pix += wid)
          for (int piy = -origin.piy(); piy < dimension.height; piy += wid) {
            TilePixel shift = origin.shift(pix, piy);
            TileImage tileImage = mapImagesCache.getTile(shift.tile());
            missing |= Objects.isNull(tileImage);
          }
      }
      if (missing) {
        Optional<TilePixel> optional = origin.widIncr(-256, tileServer.z_max());
        if (optional.isPresent()) {
          TilePixel coarse = optional.orElseThrow();
          for (int ix = -coarse.pix(); ix < dimension.width / 2; ix += wid)
            for (int iy = -coarse.piy(); iy < dimension.height / 2; iy += wid) {
              TilePixel shift = coarse.shift(ix, iy);
              TileImage tileImage = mapImagesCache.getTile(shift.tile());
              if (Objects.nonNull(tileImage))
                graphics.drawImage(tileImage.bufferedImage(), ix * 2, iy * 2, wid * 2, wid * 2, null);
            }
        }
      }
      {
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
      }
      graphics.setClip(shape);
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
    GeoLayer geoLayer = new GeoLayer(origin, showableConfig);
    if (ticks && 2 < tilePixel.tile().z()) {
      ScalarUnaryOperator suo = UnitConvert.SI().to(Degree.UNIT);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      if (true) { // lon along "X - axis"
        Scalar min = suo.apply(tilePixel.shift(-center.x, 0).lon_lat().Get(0));
        Scalar max = suo.apply(tilePixel.shift(+center.x, 0).lon_lat().Get(0));
        while (Scalars.lessThan(max, min))
          max = max.add(Quantity.of(360, "deg"));
        Clip clip = Clips.interval(min, max);
        if (Scalars.nonZero(clip.length())) {
          List<Scalar> list = Ticks.stream(clip, Rational.of(100, dimension.width)).toList();
          Tensor lon_lat = tilePixel.lon_lat().maps(suo);
          int height = fontMetrics.getDescent();
          graphics.setStroke(new BasicStroke()); // thickness of outline
          for (Scalar tick : list) {
            lon_lat.set(tick, 0);
            Point point = geoLayer.toPoint(lon_lat);
            int x = point.x;
            if (gridlines) {
              graphics.setColor(gridLineColor);
              graphics.drawLine(x, 0, x, dimension.height);
            }
            int y = dimension.height - 20;
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x, y - 10, x, dimension.height);
            textContour.draw(" " + Ticks.format(tick), x, dimension.height - height - 2);
          }
        }
      }
      if (true) { // lat along "Y - axis"
        Scalar max = suo.apply(tilePixel.shift(0, -center.y).lon_lat().Get(1));
        Scalar min = suo.apply(tilePixel.shift(0, +center.y).lon_lat().Get(1));
        while (Scalars.lessThan(max, min))
          max = max.add(Quantity.of(180, "deg"));
        Clip clip = Clips.interval(min, max);
        List<Scalar> list = Ticks.stream(clip, Rational.of(100, dimension.height)).toList();
        Tensor lon_lat = tilePixel.lon_lat().maps(suo);
        // establish longest string, e.g. "47.15"
        OptionalInt optionalInt = list.stream().map(Ticks::format).mapToInt(fontMetrics::stringWidth).max();
        if (optionalInt.isPresent()) {
          int width = optionalInt.getAsInt() + 5;
          graphics.setStroke(new BasicStroke()); // thickness of outline
          for (Scalar tick : list) {
            lon_lat.set(tick, 1);
            Point point = geoLayer.toPoint(lon_lat);
            int x = dimension.width - width;
            int y = point.y;
            if (gridlines) {
              graphics.setColor(gridLineColor);
              lon_lat.set(tick, 0);
              graphics.drawLine(0, point.y, dimension.width, point.y);
            }
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
      textContour.draw("z=" + tilePixel.tile().z() + " wid=" + tilePixel.wid(), 2, 20);
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
