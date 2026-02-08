package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public class TextsPlot extends BaseShowable {
  public static TextsPlot of(Map<Tensor, String> map) {
    return new TextsPlot(map);
  }

  private final Map<Tensor, String> map;

  public TextsPlot(Map<Tensor, String> map) {
    this.map = map;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    graphics.setColor(Color.BLACK);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
    for (var entry : map.entrySet()) {
      Tensor tensor = entry.getKey();
      String string = entry.getValue();
      double width_half = fontMetrics.stringWidth(string) * 0.5;
      Point2D point2d = showableConfig.toPoint2D(tensor);
      graphics.drawString(string, //
          (float) (point2d.getX() - width_half), //
          (float) (point2d.getY() + delta_y));
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBounds.of(Tensor.of(map.keySet().stream())));
  }
}
