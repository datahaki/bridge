// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.bridge.fig.BackgroundPlotMarker;
import ch.alpine.bridge.fig.BarLegend;
import ch.alpine.bridge.fig.BarLegendPlot;
import ch.alpine.bridge.fig.Meshgrid;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Timing;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DensityPlot.html">DensityPlot</a> */
// TODO BRIDGE option to constrain area by initial Clip
public class DensityPlot extends BarLegendPlot implements BackgroundPlotMarker {
  private static final Scalar RENDER_TIME_TARGET = Quantity.of(0.1, "s");
  private static final int RESOLUTION_DEFAULT = 80;
  private static final int RESOLUTION_MIN = 10;

  /** @param sbo
   * @param cbb
   * @return */
  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb) {
    return of(sbo, cbb, ColorDataGradients.DENSITY);
  }

  /** @param sbo
   * @param cbb
   * @param colorDataGradient
   * @return */
  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return new DensityPlot(sbo, cbb, colorDataGradient);
  }

  /** non-evaluating
   * 
   * @param matrix
   * @param cbb
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return ArrayPlot.of(matrix, cbb, colorDataGradient, false);
  }

  // ---
  private final Cache<Meshgrid, Inner> cache = Cache.of(this::recompute, 1);
  private final ScalarBinaryOperator sbo;
  private final ScalarTensorFunction colorDataGradient;
  // ---
  private Clip inner_clip = null;
  private int resolution = RESOLUTION_DEFAULT;

  private class Inner {
    private final ScalableImage scalableImage;
    private final Clip clip;

    public Inner(Meshgrid meshgrid) {
      Rescale rescale = new Rescale(meshgrid.image(sbo));
      BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
      scalableImage = new ScalableImage(bufferedImage);
      // IO.println("w="+bufferedImage.getWidth()+" h="+bufferedImage.getHeight());
      clip = rescale.clip();
    }
  }

  private DensityPlot(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    super(cbb);
    this.sbo = sbo;
    this.colorDataGradient = colorDataGradient;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    CoordinateBoundingBox cbb = showableConfig.cbb();
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        cbb.clip(1).max()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        cbb.clip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height) {
      Meshgrid meshgrid = Meshgrid.of(cbb, StaticHelper.aspect(resolution, showableConfig.rectangle().getSize()));
      graphics.drawImage( //
          cache.apply(meshgrid).scalableImage.getScaledInstance(ImageResize.DEGREE_3, width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), //
          null);
      // debug info
      graphics.setColor(new Color(128, 128, 128, 128));
      graphics.drawString("" + meshgrid.width() + " " + meshgrid.height(), //
          (int) ul.getX(), //
          (int) ul.getY() + 12); // magic const dep on fontsize
    }
  }

  private Inner recompute(Meshgrid meshgrid) {
    Timing timing = Timing.started();
    Inner inner = new Inner(meshgrid);
    resolution += Scalars.lessThan(timing.seconds(), RENDER_TIME_TARGET) //
        ? +7
        : -7;
    resolution = Math.max(RESOLUTION_MIN, resolution);
    inner_clip = inner.clip;
    return inner;
  }

  @Override
  protected BarLegend barLegend() {
    return Objects.nonNull(inner_clip) //
        ? new BarLegend(inner_clip, colorDataGradient)
        : null;
  }

  public void setPlotPoints(int resolution) {
    this.resolution = resolution;
    cache.clear();
  }

  public ScalableImage getScalableImage(int res) {
    return cache.apply(Meshgrid.of(cbb, res)).scalableImage;
  }
}
