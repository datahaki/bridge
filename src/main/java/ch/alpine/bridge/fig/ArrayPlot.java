// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public class ArrayPlot implements Showable {
  // /** function emulates ArrayPlot[matrix] in Mathematica
  // *
  // * Other that in Mathematica, the axes are drawn with ticks.
  // *
  // * Hint:
  // * use {@link Showable#setTitle(String)} to define plot label
  // *
  // * @param matrix
  // * @return */
  // public static Showable of(Tensor matrix) {
  // return new ArrayPlot(VisualImage.of(matrix), null);
  // }
  // // public static Showable of(Tensor matrix, ColorDataGradient colorDataGradient) {
  // // return of(VisualImage.of(matrix, colorDataGradient));
  // // }
  /** @param visualImage
   * @return */
  private final BufferedImage bufferedImage;
  private final CoordinateBoundingBox cbb;

  public ArrayPlot(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    this.bufferedImage = bufferedImage;
    this.cbb = cbb;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics _g) {
    Point2D.Double ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).min(), //
        cbb.getClip(1).max()));
    Point2D.Double dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).max(), //
        cbb.getClip(1).min()));
    
    int width = (int) Math.round(dr.getX() - ul.getX());
    int height = (int) Math.round(dr.getY() - ul.getY());
    Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    // graphics.drawImage(image, 0, 0, null);
    _g.drawImage(image, //
        (int) ul.getX(), //
        (int) ul.getY(), null);
  }

  @Override
  public void setLabel(String string) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setColor(Color color) {
    // TODO Auto-generated method stub
  }
}
