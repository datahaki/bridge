// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.fig.grd.AxisOptions;
import ch.alpine.bridge.fig.grd.ConfLatDecr;
import ch.alpine.bridge.fig.grd.ConfLin;
import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.math.Unitless;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public class ShowOptions implements Serializable {
  private static final ScalarUnaryOperator TO_DEGREE = Unitless.DEGREES.unitConvert();
  // ---
  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;
  public static final int TICK = 3;
  // ---
  private final Set<ShowOption> set = EnumSet.allOf(ShowOption.class);
  public final AxisOptions axisOptionsX = new AxisOptions();
  public final AxisOptions axisOptionsY = new AxisOptions();
  Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
  String showLabel = "";

  public ShowableConfig config(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        axisOptionsX.conf(false, rectangle.x, rectangle.width, cbb.clip(0)), //
        axisOptionsY.conf(true, rectangle.y, rectangle.height, cbb.clip(1)));
  }

  public ShowableConfig geo(Rectangle rectangle, TilePixel origin) {
    Point p00 = rectangle.getLocation();
    Point p11 = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
    TilePixel tp00 = origin.shift(p00.x, p00.y);
    TilePixel tp11 = origin.shift(p11.x, p11.y);
    Tensor lon_lat0 = tp00.lon_lat();
    Tensor lon_lat1 = tp11.lon_lat();
    CoordinateBoundingBox cbb = CoordinateBounds.of(Tensors.of(lon_lat0, lon_lat1).maps(TO_DEGREE));
    return new ShowableConfig(rectangle, cbb, //
        ConfLin.create(rectangle.x, rectangle.width, cbb.clip(0), true), //
        new ConfLatDecr(rectangle.y, rectangle.height, cbb.clip(1), origin));
  }

  public void set(ShowOption showOption, boolean status) {
    if (status)
      set.add(showOption);
    else
      set.remove(showOption);
  }

  public boolean contains(ShowOption showOption) {
    return set.contains(showOption);
  }
}
