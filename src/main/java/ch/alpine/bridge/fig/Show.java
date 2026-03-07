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
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.chq.ExactScalarQ;
import ch.alpine.tensor.ext.Jpeg;
import ch.alpine.tensor.ext.PathName;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Round;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Show.html">Show</a> */
public class Show implements Serializable {
  // TODO BRIDGE zoom does not work indefinitely yet!
  private static final Color COLOR_FRAME = new Color(160, 160, 160);

  /** @param fontSize for instance graphics.getFont().getSize()
   * @return */
  private static Insets defaultInsets(int fontSize) {
    return new Insets(4 + fontSize, 70, 10 + fontSize, 10);
  }

  /** @param dimension
   * @param fontSize for instance graphics.getFont().getSize()
   * @return */
  public static Rectangle defaultInsets(Dimension dimension, int fontSize) {
    Insets insets = defaultInsets(fontSize);
    return new Rectangle( //
        insets.left, //
        insets.top, //
        dimension.width - insets.left - insets.right, //
        dimension.height - insets.top - insets.bottom);
  }

  // ---
  private final List<Showable> showables = new ArrayList<>();
  private final ColorDataIndexed colorDataIndexed;
  // ---
  private CoordinateBoundingBox cbb = null;
  private DateTimeFocus dateTimeFocus = ISO8601DateTimeFocus.INSTANCE;
  private boolean framed = true;
  private boolean gridLines = true;
  private String plotLabel = "";

  /** @param colorDataIndexed to assign a default color to a showable when
   * passed via {@link #add(Showable)} */
  public Show(ColorDataIndexed colorDataIndexed) {
    this.colorDataIndexed = Objects.requireNonNull(colorDataIndexed);
  }

  /** uses Mathematica default color scheme */
  public Show() {
    this(ColorDataLists._097.cyclic());
  }

  /** @param showable
   * @return given showable */
  public final Showable add(Showable showable) {
    showable.setColor(colorDataIndexed.getColor(showables.size()));
    if (showable instanceof BarLegendPlot barLegendPlot && //
        barLegendPlot.getAspectRatioOneHint())
      setAspectRatioOne();
    showables.add(showable);
    return showable;
  }

  /** @param string to appear above plot */
  public final void setPlotLabel(String string) {
    plotLabel = Objects.requireNonNull(string);
  }

  /** @return */
  public final String getPlotLabel() {
    return plotLabel;
  }

  // TODO BRIDGE offer more fine grain control: gridline and axes are currently coupled
  public final void setGridLines(boolean gridLines) {
    this.gridLines = gridLines;
  }

  public final boolean getGridLines() {
    return gridLines;
  }

  public boolean isFramed() {
    return framed;
  }

  public void setFramed(boolean framed) {
    this.framed = framed;
  }

  /** @param cbb null is permitted in which case the function
   * {@link #render(Graphics, Rectangle)} determines the coordinate bounds */
  public void setCbb(CoordinateBoundingBox cbb) {
    this.cbb = Objects.isNull(cbb) //
        ? cbb
        : StaticHelper.nonZero(cbb);
  }

  /** TODO current design is so that value is calculated only after drawing :-(
   * 
   * @return may be null */
  public CoordinateBoundingBox getCbb() {
    return cbb;
  }

  private CoordinateBoundingBox deriveCbb() {
    if (Objects.isNull(getCbb()))
      showables.stream() //
          .flatMap(showable -> showable.fullPlotRange().stream()) //
          .reduce(CoordinateBounds::cover) //
          .ifPresent(this::setCbb);
    return getCbb();
  }

  public void setDateTimeFocus(DateTimeFocus dateTimeFocus) {
    this.dateTimeFocus = Objects.requireNonNull(dateTimeFocus);
  }

  public DateTimeFocus getDateTimeFocus() {
    return dateTimeFocus;
  }

  public boolean isEmpty() {
    return showables.isEmpty();
  }

  private Scalar aspectRatio = null;

  /** @param xStep exact scalar, for instance 1
   * @param yStep
   * @see ExactScalarQ */
  public void setAspectRatio(Scalar xStep, Scalar yStep) {
    // TODO BRIDGE throw exception if axis X and Y are not compatible unit etc.
    this.aspectRatio = ExactScalarQ.require(xStep.divide(yStep));
  }

  /** Remark: our implementation is inconsistent with Mathematica
   * Mathematica::"Automatic" is 1 in the tensor lib */
  public void setAspectRatioOne() {
    setAspectRatio(RealScalar.ONE, RealScalar.ONE);
  }

  public void setAspectRatioDontCare() {
    aspectRatio = null;
  }

  public Scalar getAspectRatio() {
    return aspectRatio;
  }

  /** @param graphics
   * @param rectangle
   * @return null if input rectangle is unsuitable for drawing */
  public ShowableConfig render(Graphics graphics, Rectangle rectangle) {
    if (rectangle.width <= 1 || rectangle.height <= 1)
      return null;
    CoordinateBoundingBox _cbb = deriveCbb();
    Rectangle r = new Rectangle(rectangle);
    if (Objects.nonNull(_cbb)) {
      Scalar aspect = aspectRatio;
      if (Objects.isNull(aspect)) {
        Set<Scalar> set = showables.stream() //
            .map(Showable::aspectRatioHint) //
            .flatMap(Optional::stream) //
            .collect(Collectors.toSet());
        if (set.size() == 1)
          aspect = set.iterator().next();
      }
      if (Objects.nonNull(aspect)) {
        Tensor a = Tensor.of(_cbb.stream().map(Clip::width));
        a.set(aspect::multiply, 1);
        Tensor b = Tensors.vector(rectangle.width, rectangle.height);
        Optional<Tensor> optional = CbbFit.inside(a, b);
        if (optional.isEmpty())
          return null;
        Tensor c = optional.orElseThrow();
        r.width = Round.intValueExact(c.Get(0));
        r.height = Round.intValueExact(c.Get(1));
      }
    }
    renderFrameTitle(graphics, r);
    Graphics2D g = (Graphics2D) graphics.create();
    g.setClip(r.x, r.y, r.width, r.height);
    RenderQuality.setQuality(g);
    ShowableConfig showableConfig = renderShowables(graphics, g, r);
    renderLegend(g, r);
    g.dispose();
    return showableConfig;
  }

  /** @param graphics
   * @param rectangle
   * @return */
  public ShowableConfig render_autoIndent(Graphics graphics, Rectangle rectangle) {
    Rectangle r = defaultInsets(rectangle.getSize(), graphics.getFont().getSize());
    return render(graphics, new Rectangle(rectangle.x + r.x, rectangle.y + r.y, r.width, r.height));
  }

  /** @param dimension
   * @return */
  public BufferedImage image(Dimension dimension) {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    render(graphics, defaultInsets(dimension, graphics.getFont().getSize()));
    graphics.dispose();
    return bufferedImage;
  }

  /** @param dimension
   * @param rectangle
   * @return */
  public BufferedImage image(Dimension dimension, Rectangle rectangle) {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    render(graphics, rectangle);
    graphics.dispose();
    return bufferedImage;
  }

  private static final float JPG_QUALITY = 0.98f;

  /** @param path
   * @param dimension of image
   * @throws IOException */
  public void export(Path path, Dimension dimension) throws IOException {
    String string = PathName.of(path).extension();
    BufferedImage bufferedImage = image(dimension);
    switch (string) {
    case "jpg", "jpeg" -> Jpeg.put(bufferedImage, path, JPG_QUALITY);
    default -> {
      try (OutputStream outputStream = Files.newOutputStream(path)) {
        ImageIO.write(bufferedImage, string, outputStream);
      }
    }
    }
  }

  // ---
  private void renderFrameTitle(Graphics _g, Rectangle rectangle) {
    Graphics2D graphics = (Graphics2D) _g.create();
    if (isFramed()) {
      // draw box around ...
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(Show.COLOR_FRAME);
      graphics.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
    }
    {
      String string = getPlotLabel();
      if (!string.isEmpty()) {
        Font font = _g.getFont().deriveFont(Font.BOLD);
        graphics.setFont(font);
        RenderQuality.setQuality(graphics);
        graphics.setColor(StaticHelper.COLOR_FONT);
        graphics.drawString(string, rectangle.x, rectangle.y - StaticHelper.GAP);
      }
    }
    graphics.dispose();
  }

  private ShowableConfig renderShowables(Graphics _g, Graphics2D graphics, Rectangle rectangle) {
    final ShowableConfig showableConfig;
    CoordinateBoundingBox _cbb = deriveCbb();
    if (Objects.isNull(_cbb)) {
      showableConfig = null;
      graphics.setColor(Color.DARK_GRAY);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      String string = "no data";
      double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
      graphics.drawString(string, //
          rectangle.x + (rectangle.width - fontMetrics.stringWidth(string)) / 2, //
          rectangle.y + (int) (rectangle.height * 0.5 + delta_y));
    } else {
      boolean flipY = showables.stream().anyMatch(Showable::flipYAxis);
      showableConfig = flipY //
          ? new ShowableConfigYF(rectangle, _cbb)
          : new ShowableConfig(rectangle, _cbb);
      if (gridLines) {
        GridDrawer gridDrawer = new GridDrawer(dateTimeFocus);
        gridDrawer.render(showableConfig, _g);
      }
      for (Showable showable : showables) {
        showable.render(showableConfig, graphics);
        showable.tender(showableConfig, _g);
      }
    }
    return showableConfig;
  }

  private void renderLegend(Graphics2D graphics, Rectangle rectangle) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int size = fontMetrics.getHeight();
    int pix = rectangle.x + StaticHelper.GAP;
    final int ystart = rectangle.y + 2 - fontMetrics.getDescent();
    {
      int piy = ystart;
      graphics.setColor(new Color(255, 255, 255, 192));
      for (Showable showable : showables) {
        String string = showable.getLabel();
        if (!string.isEmpty()) {
          graphics.fillRect(pix, piy, fontMetrics.stringWidth(string), size);
          // showarea.setColor(Color.RED);
          // showarea.drawRect(pix, piy, fontMetrics.stringWidth(string), size);
          piy += size;
        }
      }
    }
    {
      int piy = ystart;
      for (Showable showable : showables) {
        String string = showable.getLabel();
        if (!string.isEmpty()) {
          piy += size;
          graphics.setColor(showable.getColor());
          graphics.drawString(string, pix, piy - 3);
        }
      }
    }
  }
}
