// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.imageio.ImageIO;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Show.html">Show</a> */
public class Show implements Serializable {
  public static Insets defaultInsets() {
    return new Insets(15, 70, 25, 5);
  }

  private final List<Showable> showables = new ArrayList<>();
  private final ColorDataIndexed colorDataIndexed;
  private CoordinateBoundingBox cbb = null;
  boolean frame = true;

  public Show(ColorDataIndexed colorDataIndexed) {
    this.colorDataIndexed = Objects.requireNonNull(colorDataIndexed);
  }

  /** uses Mathematica default color scheme */
  public Show() {
    this(ColorDataLists._097.cyclic());
  }

  private String plotLabel = "";
  static final Color COLOR_FRAME = new Color(160, 160, 160);

  /** @param string to appear above plot */
  public final void setPlotLabel(String string) {
    plotLabel = string;
  }

  /** @return */
  public final String getPlotLabel() {
    return plotLabel;
  }

  public Showable add(Showable showable) {
    Color color = colorDataIndexed.getColor(showables.size());
    showable.setColor(color);
    showables.add(showable);
    return showable;
  }

  public void render(Graphics graphics, Rectangle rectangle) {
    CoordinateBoundingBox _cbb = cbb;
    if (Objects.isNull(_cbb)) {
      Optional<CoordinateBoundingBox> optional = showables.stream() //
          .flatMap(s -> s.fullPlotRange().stream()) //
          .reduce(CoordinateBounds::cover);
      if (optional.isPresent()) {
        _cbb = optional.orElseThrow();
      }
    }
    Graphics2D showarea = (Graphics2D) graphics.create();
    if (frame) {
      showarea.setStroke(StaticHelper.STROKE_SOLID);
      showarea.setColor(Show.COLOR_FRAME);
      showarea.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
    }
    {
      Graphics2D showarea2 = (Graphics2D) graphics.create();
      Font font = showarea2.getFont().deriveFont(Font.BOLD);
      showarea2.setFont(font);
      RenderQuality.setQuality(showarea2);
      showarea2.setColor(StaticHelper.COLOR_FONT);
      showarea2.drawString(plotLabel, rectangle.x, rectangle.y - StaticHelper.GAP);
      showarea2.dispose();
    }
    showarea.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    if (Objects.isNull(_cbb)) {
      showarea.setColor(Color.DARK_GRAY);
      RenderQuality.setQuality(showarea);
      FontMetrics fontMetrics = showarea.getFontMetrics();
      String string = "no data";
      showarea.drawString(string, //
          rectangle.x + (rectangle.width - fontMetrics.stringWidth(string)) / 2, //
          rectangle.y + (rectangle.height + fontMetrics.getHeight()) / 2);
    } else {
      ShowableConfig showableConfig = new ShowableConfig(rectangle, _cbb);
      GridDrawer gridDrawer = new GridDrawer(showableConfig);
      gridDrawer.render(graphics);
      for (Showable showable : showables)
        showable.render(showableConfig, showarea);
    }
    showarea.dispose();
  }

  /** @param file
   * @param dimension of image
   * @throws IOException */
  public void export(File file, Dimension dimension) throws IOException {
    Insets insets = defaultInsets();
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    Rectangle rectangle = new Rectangle( //
        insets.left, //
        insets.top, //
        dimension.width - insets.left - insets.right, //
        dimension.height - insets.bottom);
    render(graphics, rectangle);
    String string = file.toString();
    int index = string.lastIndexOf('.');
    ImageIO.write(bufferedImage, string.substring(index + 1), file);
  }

  public void setCbb(CoordinateBoundingBox cbb) {
    this.cbb = cbb;
  }

  public boolean isEmpty() {
    return showables.isEmpty();
  }
}
