// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.chq.ExactScalarQ;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Round;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Show.html">Show</a> */
// TODO BRIDGE zoom does not work indefinitely yet!
public final class Show implements Serializable {
  private final List<Showable> showables = new ArrayList<>();
  private final ShowOptions showOptions = new ShowOptions();
  private final ColorDataIndexed colorDataIndexed;
  // ---
  private CoordinateBoundingBox cbb = null;
  private Scalar aspectRatio = null;

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
  public Showable add(Showable showable) {
    showable.setColor(colorDataIndexed.getColor(showables.size()));
    if (showable instanceof BarLegendPlot barLegendPlot && //
        barLegendPlot.getAspectRatioOneHint())
      setAspectRatioOne();
    showable.disableOptions() //
        .forEach(showOption -> showOptions.set(showOption, false));
    showables.add(showable);
    return showable;
  }

  /** @param string to appear above plot */
  public void setShowLabel(String string) {
    showOptions.showLabel = Objects.requireNonNull(string);
  }

  /** @return */
  public String getShowLabel() {
    return showOptions.showLabel;
  }

  public void setFont(Font font) {
    showOptions.font = font;
  }

  public void set(ShowOption showOption, boolean status) {
    showOptions.set(showOption, status);
  }

  /** @param cbb null is permitted in which case the function
   * {@link #render(Graphics, Rectangle)} determines the coordinate bounds */
  public void setCbb(CoordinateBoundingBox cbb) {
    this.cbb = Objects.isNull(cbb) //
        ? cbb
        : StaticHelper.nonZero(cbb);
  }

  /** @return may be null */
  // TODO BRIDGE current design is so that value is calculated only after drawing :-(
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
    showOptions.dateTimeFocus = Objects.requireNonNull(dateTimeFocus);
  }

  public DateTimeFocus getDateTimeFocus() {
    return showOptions.dateTimeFocus;
  }

  public boolean isEmpty() {
    return showables.isEmpty();
  }

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
  public ShowableConfig render(Graphics _g, Rectangle rectangle) {
    if (rectangle.width <= 1 || rectangle.height <= 1)
      return null;
    CoordinateBoundingBox cbb = deriveCbb();
    if (Objects.nonNull(cbb)) {
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
        Tensor a = Tensor.of(cbb.stream().map(Clip::length));
        a.set(aspect::multiply, 1);
        Tensor b = Tensors.vector(rectangle.width, rectangle.height);
        Optional<Tensor> optional = CbbFit.inside(a, b);
        if (optional.isEmpty())
          return null;
        Tensor c = optional.orElseThrow();
        rectangle.width = Round.intValueExact(c.Get(0));
        rectangle.height = Round.intValueExact(c.Get(1));
      }
    }
    return new ShowRender(showables, showOptions, cbb).render(_g, rectangle);
  }

  /** @param graphics
   * @param rectangle
   * @return */
  public ShowableConfig render_autoIndent(Graphics graphics, Rectangle rectangle) {
    graphics.setFont(showOptions.font);
    return render(graphics, defaultInsets(rectangle, graphics.getFontMetrics()));
  }

  /** function allows to draw grid
   * 
   * @param dimension
   * @param fontMetrics
   * @return */
  public static Optional<Rectangle> optionalDefaultInsets(Dimension dimension, FontMetrics fontMetrics) {
    Rectangle rectangle = defaultInsets(new Rectangle(new Point(), dimension), fontMetrics);
    return Optional.ofNullable(1 < rectangle.width && 1 < rectangle.height //
        ? rectangle
        : null);
  }

  /** Careful: the width, or height of the returned rectangle may be negative
   * 
   * @param dimension
   * @param fontSize for instance graphics.getFont().getSize()
   * @return */
  private static Rectangle defaultInsets(Rectangle rectangle, FontMetrics fontMetrics) {
    int fontAd = fontMetrics.getAscent() + fontMetrics.getDescent();
    Insets insets = new Insets( //
        fontAd + 1, // top showLabel + frame width
        70, // left
        1 + StaticHelper.GAP + StaticHelper.TICK + fontAd, // bottom
        10); // right
    return new Rectangle( //
        rectangle.x + insets.left, //
        rectangle.y + insets.top, //
        rectangle.width - insets.left - insets.right, //
        rectangle.height - insets.top - insets.bottom);
  }
}
