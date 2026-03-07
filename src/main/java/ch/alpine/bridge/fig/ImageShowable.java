// code by jph
package ch.alpine.bridge.fig;

import java.util.Objects;

import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public abstract class ImageShowable extends BarLegendPlot {
  private ImageResize imageResize = ImageResize.DEGREE_0;

  public ImageShowable(CoordinateBoundingBox cbb) {
    super(cbb);
    Integers.requireEquals(cbb.dimensions(), 2);
  }

  public final ImageResize getImageResize() {
    return imageResize;
  }

  public final void setImageResize(ImageResize imageResize) {
    this.imageResize = Objects.requireNonNull(imageResize);
  }
}
