// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public class StringPlot extends BaseShowable {
  public record StringItem(Tensor pos, String string, Color color) implements Serializable {
    public static StringItem of(Tensor pos, String string) {
      return new StringItem(pos, string, Color.BLACK);
    }
  }

  public static StringPlot of(List<StringItem> list) {
    return new StringPlot(list);
  }

  // ---
  private final List<StringItem> list;

  private StringPlot(List<StringItem> list) {
    this.list = list;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
    for (StringItem stringItem : list) {
      graphics.setColor(stringItem.color);
      String string = stringItem.string;
      double width_half = fontMetrics.stringWidth(string) * 0.5;
      Point2D point2d = showableConfig.toPoint2D(stringItem.pos);
      // TODO could use TextContour...
      graphics.drawString(string, //
          (float) (point2d.getX() - width_half), //
          (float) (point2d.getY() + delta_y));
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBounds.of(Tensor.of(list.stream().map(StringItem::pos))));
  }
}
