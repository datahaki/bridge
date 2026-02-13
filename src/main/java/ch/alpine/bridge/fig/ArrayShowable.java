// code by jph
package ch.alpine.bridge.fig;

import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;

abstract class ArrayShowable extends BaseShowable {
  protected final CoordinateBoundingBox cbb;

  public ArrayShowable(CoordinateBoundingBox cbb) {
    this.cbb = Objects.requireNonNull(cbb);
    Integers.requireEquals(cbb.dimensions(), 2);
  }

  @Override // from Showable
  public final Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  private ImageResize imageResize = ImageResize.DEGREE_0;

  public final ImageResize getImageResize() {
    return imageResize;
  }

  public final void setImageResize(ImageResize imageResize) {
    this.imageResize = Objects.requireNonNull(imageResize);
  }

  private boolean aspectRatioOneHint = true;

  public final boolean getAspectRatioOneHint() {
    Scalar ratio = cbb.clip(0).width().divide(cbb.clip(1).width());
    Unit unit = QuantityUnit.of(ratio);
    return aspectRatioOneHint //
        && Unit.ONE.equals(unit);
  }

  public final void setAspectRatioOne(boolean hint) {
    aspectRatioOneHint = hint;
  }
}
