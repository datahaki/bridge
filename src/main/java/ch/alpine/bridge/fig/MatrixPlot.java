// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPlot.html">MatrixPlot</a> */
public class MatrixPlot extends BarLegendPlot {
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient, boolean symmetrize) {
    return new MatrixPlot(matrix, colorDataGradient, symmetrize);
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static Showable of(Tensor matrix, ScalarTensorFunction colorDataGradient) {
    return of(matrix, colorDataGradient, true);
  }

  /** @param matrix
   * @return */
  public static Showable of(Tensor matrix) {
    return of(matrix, ColorDataGradients.TEMPERATURE_LIGHT);
  }

  // ---
  private final BufferedImage bufferedImage;
  private final CoordinateBoundingBox cbb;
  private final Clip clip;

  private MatrixPlot( //
      Tensor matrix, //
      ScalarTensorFunction colorDataGradient, //
      boolean symmetrize) {
    super(colorDataGradient);
    MatrixQ.require(matrix);
    Clip clip = matrix.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
    if (Objects.nonNull(clip) && symmetrize) {
      if (clip.min() instanceof DateTime)
        System.err.println("bypass symmetrize");
      else
        clip = Clips.absolute(Max.of(clip.min().negate(), clip.max()));
    }
    Rescale rescale = new Rescale(matrix, clip);
    this.bufferedImage = ImageFormat.of(rescale.result().map(colorDataGradient));
    this.cbb = CoordinateBoundingBox.of( //
        StaticHelper.TRANSLATION.apply(Clips.positive(Unprotect.dimension1(matrix))), //
        StaticHelper.TRANSLATION.apply(Clips.positive(matrix.length())));
    this.clip = rescale.clip();
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        cbb.clip(1).min()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        cbb.clip(1).max()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height)
      graphics.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  @Override // from BarLegendPlot
  protected Clip clip() {
    return clip;
  }

  @Override // from Showable
  public boolean flipYAxis() {
    return true;
  }

  @Override // from Showable
  public Optional<Scalar> aspectRatioHint() {
    return Optional.of(RealScalar.ONE);
  }
}
