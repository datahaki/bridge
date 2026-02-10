package ch.alpine.bridge.fig;

import java.util.Objects;

import ch.alpine.tensor.img.ImageResize;

abstract class ArrayShowable extends BaseShowable {
  private ImageResize imageResize = ImageResize.DEGREE_0;

  public final ImageResize getImageResize() {
    return imageResize;
  }

  public final void setImageResize(ImageResize imageResize) {
    this.imageResize = Objects.requireNonNull(imageResize);
  }
}
