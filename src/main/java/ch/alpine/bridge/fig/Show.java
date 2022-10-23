// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Show.html">Show</a> */
public class Show extends VisualBase {
  private static final Insets INSETS = new Insets(5, 70, 25, 5);
  private final List<Showable> showables = new ArrayList<>();
  private final ColorDataIndexed colorDataIndexed;
  private CoordinateBoundingBox cbb = null;

  public Show(ColorDataIndexed colorDataIndexed) {
    this.colorDataIndexed = Objects.requireNonNull(colorDataIndexed);
  }

  /** uses Mathematica default color scheme */
  public Show() {
    this(ColorDataLists._097.cyclic());
  }

  public Showable add(Showable showable) {
    Color color = colorDataIndexed.getColor(showables.size());
    showable.setColor(color);
    showables.add(showable);
    return showable;
  }

  public void render(Rectangle rectangle, Graphics graphics) {
    CoordinateBoundingBox _cbb = Objects.isNull(cbb) //
        ? showables.stream().flatMap(s -> s.fullPlotRange().stream()).reduce(CoordinateBounds::cover).orElseThrow()
        : cbb;
    GridDrawer gridDrawer = new GridDrawer(rectangle, _cbb);
    gridDrawer.render(graphics);
    ShowableConfig showableConfig = new ShowableConfig(rectangle, _cbb);
    Graphics2D showarea = (Graphics2D) graphics.create();
    showarea.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    for (Showable showable : showables)
      showable.render(showableConfig, showarea);
    showarea.dispose();
  }
  // /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}.
  // * The special case when points == {} is also allowed.
  // * @return instance of the visual row, that was added to this visual set
  // * @throws Exception if not all entries in points are vectors of length 2 */
  // public VisualRow add(Tensor points) {
  // points.stream().forEach(row -> VectorQ.requireLength(row, 2));
  // return _add(points);
  // }
  //
  // /** @param domain {x1, x2, ..., xn}
  // * @param values {y1, y2, ..., yn}
  // * @return */
  // public VisualRow add(Tensor domain, Tensor values) {
  // return add(Transpose.of(Tensors.of(domain, values)));
  // }
  // /** @param timeSeries
  // * @param function mapping a {@link Tensor} value to a {@link Scalar} along the y-axis
  // * @return */
  // public VisualRow add(TimeSeries timeSeries, TensorScalarFunction function) {
  // return _add(Tensor.of(timeSeries.stream() //
  // .map(entry -> Tensors.of(entry.key(), function.apply(entry.value())))));
  // }
  //
  // /** @param timeSeries with {@link Scalar} as values
  // * @return */
  // public VisualRow add(TimeSeries timeSeries) {
  // return add(timeSeries, Scalar.class::cast);
  // }
  //
  // private VisualRow _add(Tensor points) {
  // if (Tensors.nonEmpty(points)) {
  // if (!getAxisX().hasUnit())
  // getAxisX().setUnit(QuantityUnit.of(points.Get(0, 0)));
  // if (!getAxisY().hasUnit())
  // getAxisY().setUnit(QuantityUnit.of(points.Get(0, 1)));
  // }
  // final int index = visualRows.size();
  // VisualRow visualRow = new VisualRow(points, index);
  // visualRow.setColor(colorDataIndexed.getColor(index));
  // visualRows.add(visualRow);
  // return visualRow;
  // }
  // Optional<Clip> suggestClip(int index) {
  // return visualRows.stream() //
  // .flatMap(rw -> rw.pointsClip(index).stream()) //
  // .reduce(Clips::cover);
  // }
  //
  // public List<VisualRow> visualRows() {
  // return Collections.unmodifiableList(visualRows);
  // }
  //
  // public VisualRow getVisualRow(int index) {
  // return visualRows.get(index);
  // }
  //
  // public boolean hasLegend() {
  // return visualRows.stream() //
  // .map(VisualRow::getLabelString) //
  // .anyMatch(Predicate.not(String::isEmpty));
  // }
  //
  // public Show setJoined(boolean joined) {
  // for (VisualRow visualRow : visualRows)
  // visualRow.setJoined(joined);
  // return this;
  // }

  public void export(File file, Dimension dimension) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    Rectangle rectangle = new Rectangle( //
        INSETS.left, //
        INSETS.top, //
        dimension.width - INSETS.left - INSETS.right, //
        dimension.height - INSETS.bottom);
    render(rectangle, graphics);
    String string = file.toString();
    int index = string.lastIndexOf('.');
    ImageIO.write(bufferedImage, string.substring(index + 1), file);
  }

  public void setCbb(CoordinateBoundingBox cbb) {
    this.cbb = cbb;
  }
}
