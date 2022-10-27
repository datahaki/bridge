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
import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

public class GridDrawer {
  /** ..... */
  private static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final Color COLOR_GRIDLINES = new Color(224, 224, 224);
  private static final Color COLOR_HELPER = new Color(192, 192, 192);
  // ---
  private final DateTimeFocus dateTimeFocus;
  boolean axesX = true;
  boolean axesY = true;
  boolean gridLinesX = true;
  boolean gridLinesY = true;
  boolean ticksX = true;
  boolean ticksY = true;

  public GridDrawer(DateTimeFocus dateTimeFocus) {
    this.dateTimeFocus = Objects.requireNonNull(dateTimeFocus);
  }

  public void render(ShowableConfig showableConfig, Graphics _g) {
    Rectangle rectangle = showableConfig.rectangle;
    // if (rectangle.width <= 1 || rectangle.height <= 1)
    // return;
    Clip xRange = showableConfig.getClip(0);
    Clip yRange = showableConfig.getClip(1);
    Graphics2D graphics = (Graphics2D) _g.create();
    // graphics.setFont(StaticHelper.FONT);
    // ---
    if (axesX && !Scalars.isZero(xRange.width()))
      drawXLines(showableConfig, graphics);
    if (axesY && !Scalars.isZero(yRange.width()))
      drawYLines(showableConfig, graphics);
    // ---
    {
      graphics.setColor(StaticHelper.COLOR_FONT);
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
            rectangle.x - fontMetrics.stringWidth(xLabel) - 3 * StaticHelper.GAP, //
            rectangle.y + rectangle.height - 1 + StaticHelper.GAP + fontMetrics.getHeight());
      }
    }
    // ---
    graphics.dispose();
  }

  private void drawXLines(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip xRange = showableConfig.getClip(0);
    final int y_height = rectangle.y + rectangle.height - 1;
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
        int x_pos = (int) showableConfig.x_pos(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      // TODO UTIL determine reserve
      Scalar dX = getDecimalStep(xRange.width().divide(RealScalar.of(rectangle.width)).multiply(RealScalar.of(50)));
      for (Scalar xValue = Ceiling.toMultipleOf(dX).apply(xRange.min()); Scalars.lessEquals(xValue, xRange.max()); xValue = xValue.add(dX)) {
        int x_pos = (int) showableConfig.x_pos(xValue);
        navigableMap.put(x_pos, xValue);
      }
    }
    if (gridLinesX) { // grid lines |
      graphics.setColor(COLOR_GRIDLINES);
      graphics.setStroke(STROKE_GRIDLINES);
      for (int pix : navigableMap.keySet())
        graphics.drawLine(pix, rectangle.y, pix, y_height);
    }
    if (ticksX) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine( //
            rectangle.x, //
            y_height + StaticHelper.GAP, //
            rectangle.x + rectangle.width - 1, //
            y_height + StaticHelper.GAP);
        for (int pix : navigableMap.keySet())
          graphics.drawLine(pix, y_height + StaticHelper.GAP + 1, pix, y_height + StaticHelper.GAP + 2);
      }
      {
        Graphics2D graphics2 = (Graphics2D) graphics.create();
        graphics2.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics2);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          Scalar value = entry.getValue();
          String xLabel = Objects.isNull(dateTimeFormatter) //
              ? format(value)
              : ((DateTime) value).format(dateTimeFormatter);
          graphics2.drawString(xLabel, //
              entry.getKey() - fontMetrics.stringWidth(xLabel) / 2, //
              y_height + StaticHelper.GAP + 3 + fontMetrics.getAscent());
        }
        graphics2.dispose();
      }
    }
  }

  /** draw lines and numbers like this: _________________ */
  private void drawYLines(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip yRange = showableConfig.getClip(1);
    Scalar plotHeight = RealScalar.of(rectangle.height - 1);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int fontSize = fontMetrics.getAscent();
    Scalar dY = getDecimalStep(yRange.width().divide(plotHeight).multiply(RealScalar.of(fontSize * 2)));
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    for (Scalar yValue = Ceiling.toMultipleOf(dY).apply(yRange.min()); Scalars.lessEquals(yValue, yRange.max()); yValue = yValue.add(dY)) {
      int y_pos = (int) showableConfig.y_pos(yValue);
      navigableMap.put(y_pos, yValue);
    }
    if (gridLinesY) {
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(rectangle.x, piy, rectangle.x + rectangle.width - 1, piy);
    }
    if (ticksY) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine(rectangle.x - StaticHelper.GAP, rectangle.y, rectangle.x - StaticHelper.GAP, rectangle.y + rectangle.height - 1);
        for (int piy : navigableMap.keySet())
          graphics.drawLine(rectangle.x - StaticHelper.GAP - 2, piy, rectangle.x - StaticHelper.GAP - 1, piy);
      }
      {
        Graphics2D graphics2 = (Graphics2D) graphics.create();
        // TODO UTIL 20221013 align dot's of numbers
        graphics2.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics2);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          int piy = entry.getKey();
          Scalar yValue = entry.getValue();
          String string = format(yValue);
          graphics2.drawString(string, rectangle.x - fontMetrics.stringWidth(string) - StaticHelper.GAP - 5, piy + fontSize / 2 - 1);
        }
        graphics2.dispose();
      }
    }
  }

  private static String format(Scalar value) {
    Scalar display = Unprotect.withoutUnit(value);
    Scalar scalar = IntegerQ.of(display) //
        ? display
        : N.DOUBLE.apply(display);
    return scalar.toString();
  }

  private static final Scalar[] RATIOS = { RationalScalar.of(1, 5), RationalScalar.of(1, 2) };

  /** @param quantity positive
   * @return */
  public static Scalar getDecimalStep(Scalar quantity) {
    Sign.requirePositive(quantity);
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
