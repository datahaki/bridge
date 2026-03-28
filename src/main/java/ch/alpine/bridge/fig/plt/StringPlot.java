// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ch.alpine.bridge.col.ColorPair;
import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.gfx.TextContour;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public class StringPlot extends BaseShowable {
  private static final Font FONT = new Font(Font.DIALOG, Font.BOLD, 12);

  public record StringItem(Tensor pos, Font font, Color color, String string) implements Serializable {
    public static StringItem of(Tensor pos, String string) {
      return new StringItem(pos, FONT, Color.BLACK, string);
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
    for (StringItem stringItem : list) {
      graphics.setFont(stringItem.font);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
      String string = stringItem.string;
      double width_half = fontMetrics.stringWidth(string) * 0.5;
      Point2D point2d = showableConfig.toPoint2D(stringItem.pos);
      TextContour textContour = TextContour.of(graphics);
      textContour.draw( //
          new ColorPair(stringItem.color, new Color(255, 255, 255, 128)), //
          string, //
          (float) (point2d.getX() - width_half), //
          (float) (point2d.getY() + delta_y));
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBounds.of(Tensor.of(list.stream().map(StringItem::pos))));
  }
}
