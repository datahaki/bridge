// code by legion
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

public class GridDrawer {
  /** ..... */
  private static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final Color COLOR_GRIDLINES = new Color(224, 224, 224);
  // ---
  private static final Stroke STROKE_SOLID = new BasicStroke();
  private static final Color COLOR_FRAME = new Color(160, 160, 160);
  private static final Color COLOR_FONT = Color.DARK_GRAY;
  private static final Color COLOR_HELPER = new Color(192, 192, 192);
  private static final int GAP = 4;
  // ---
  private final Rectangle rectangle;
  private final Clip xRange;
  private final Clip yRange;
  private final DateTimeFocus dateTimeFocus;

  public GridDrawer(Rectangle rectangle, CoordinateBoundingBox cbb, DateTimeFocus dateTimeFocus) {
    this.rectangle = new Rectangle(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1);
    xRange = cbb.getClip(0);
    yRange = cbb.getClip(1);
    this.dateTimeFocus = Objects.requireNonNull(dateTimeFocus);
  }

  public GridDrawer(Rectangle rectangle, CoordinateBoundingBox cbb) {
    this(rectangle, cbb, ISO8601DateTimeFocus.INSTANCE);
  }

  public void render(Graphics _g) {
    if (rectangle.height <= 0)
      return;
    Graphics2D graphics = (Graphics2D) _g.create();
    // ---
    if (!Scalars.isZero(xRange.width()))
      drawXLines(graphics);
    if (!Scalars.isZero(yRange.width()))
      drawYLines(graphics);
    // ---
    {
      graphics.setStroke(STROKE_SOLID);
      graphics.setColor(COLOR_FRAME);
      graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    {
      graphics.setColor(COLOR_FONT);
      String unit0 = Unicode.valueOf(QuantityUnit.of(xRange));
      String unit1 = Unicode.valueOf(QuantityUnit.of(yRange));
      RenderQuality.setQuality(graphics);
      if (!unit0.isEmpty() || !unit1.isEmpty()) {
        if (unit0.isEmpty())
          unit0 = "[]";
        if (unit1.isEmpty())
          unit1 = "[]";
        String xLabel = unit0 + "\u2192" + unit1;
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString(xLabel, //
            rectangle.x - fontMetrics.stringWidth(xLabel) - 3 * GAP, //
            rectangle.y + rectangle.height + GAP + fontMetrics.getHeight());
      }
    }
    // ---
    graphics.dispose();
  }

  private void drawXLines(Graphics2D graphics) {
    final int y_height = rectangle.y + rectangle.height;
    final FontMetrics fontMetrics = graphics.getFontMetrics();
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    DateTimeFormatter dateTimeFormatter = null;
    if (xRange.min() instanceof DateTime) {
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(xRange.width().multiply(RationalScalar.of(100, rectangle.width)));
      DateTime startAttempt = dateTimeInterval.floor(xRange.min());
      DateTime dateTime = xRange.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (xRange.isInside(dateTime)) {
        graphics.setColor(COLOR_FONT);
        int pix = rectangle.x + xRange.rescale(dateTime).multiply(RealScalar.of(rectangle.width)).number().intValue();
        navigableMap.put(pix, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      // TODO UTIL determine reserve
      Scalar plotWidth = RealScalar.of(rectangle.width);
      Scalar dX = getDecimalStep(xRange.width().divide(RealScalar.of(rectangle.width)), RealScalar.of(50));
      for (Scalar xValue = Ceiling.toMultipleOf(dX).apply(xRange.min()); Scalars.lessEquals(xValue, xRange.max()); xValue = xValue.add(dX)) {
        int pix = rectangle.x + xRange.rescale(xValue).multiply(plotWidth).number().intValue();
        navigableMap.put(pix, xValue);
      }
    }
    {
      graphics.setColor(COLOR_GRIDLINES);
      graphics.setStroke(STROKE_GRIDLINES);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, y_height);
    }
    {
      graphics.setStroke(STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      graphics.drawLine(rectangle.x, y_height + GAP, rectangle.x + rectangle.width, y_height + GAP);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, y_height + GAP, pix, y_height + GAP + 2);
    }
    {
      Graphics2D graphics2 = (Graphics2D) graphics.create();
      graphics2.setClip(rectangle.x - GAP, y_height, rectangle.width + GAP+ GAP, 40); // magic const
      graphics2.setColor(COLOR_FONT);
      RenderQuality.setQuality(graphics2);
      for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
        Scalar value = entry.getValue();
        String xLabel = Objects.isNull(dateTimeFormatter) ? format(value) : ((DateTime) value).format(dateTimeFormatter);
        graphics2.drawString(xLabel, entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, y_height + GAP + fontMetrics.getHeight());
      }
      graphics2.dispose();
      // graphics.setClip(null);
    }
  }

  /** draw lines and numbers like this: _________________ */
  private void drawYLines(Graphics2D graphics) {
    Scalar plotHeight = RealScalar.of(rectangle.height);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int fontSize = fontMetrics.getHeight();
    Scalar dY = getDecimalStep(yRange.width().divide(plotHeight), RealScalar.of(fontSize * 2));
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    for (Scalar yValue = Ceiling.toMultipleOf(dY).apply(yRange.min()); Scalars.lessEquals(yValue, yRange.max()); yValue = yValue.add(dY))
      navigableMap.put( //
          rectangle.y + rectangle.height - yRange.rescale(yValue).multiply(plotHeight).number().intValue(), //
          yValue);
    {
      graphics.setStroke(STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      graphics.drawLine(rectangle.x - GAP, rectangle.y, rectangle.x - GAP, rectangle.y + rectangle.height);
      for (int piy : navigableMap.keySet()) {
        graphics.drawLine(rectangle.x - GAP - 2, piy, rectangle.x - GAP, piy);
      }
    }
    {
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(rectangle.x, piy, rectangle.x + rectangle.width, piy);
    }
    {
      Graphics2D graphics2 = (Graphics2D) graphics.create();
      // TODO UTIL 20221013 align dot's of numbers
      graphics2.setColor(COLOR_FONT);
      RenderQuality.setQuality(graphics2);
      for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
        int piy = entry.getKey();
        Scalar yValue = entry.getValue();
        String string = format(yValue);
        graphics2.drawString(string, rectangle.x - fontMetrics.stringWidth(string) - GAP - 5, piy + fontSize / 2 - 1);
      }
      graphics2.dispose();
    }
  }

  private static String format(Scalar value) {
    Scalar display = Unprotect.withoutUnit(value);
    return (IntegerQ.of(display) ? display : N.DOUBLE.apply(display)).toString();
  }

  private static final Scalar[] RATIOS = { RationalScalar.of(1, 5), RationalScalar.of(1, 2) };

  /** @param widthPerPixel == valueRange.width().divide(plotHeight);
   * @param pixelMin
   * @return */
  public static Scalar getDecimalStep(Scalar widthPerPixel, Scalar pixelMin) {
    Scalar quantity = widthPerPixel.multiply(pixelMin);
    Scalar decStep = Quantity.of( //
        Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(Unprotect.withoutUnit(quantity)))), //
        QuantityUnit.of(quantity));
    return Stream.of(RATIOS) //
        .map(decStep::multiply) //
        .filter(scalar -> Scalars.lessEquals(quantity, scalar)) //
        .findFirst() //
        .orElse(decStep);
  }
}