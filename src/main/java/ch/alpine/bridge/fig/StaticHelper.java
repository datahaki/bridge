// code by gjoel, jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.red.MinMax;
import ch.alpine.tensor.sca.Clip;

/* package */ enum StaticHelper {
  ;
  /** @param bufferedImage
   * @param visualSet
   * @param domain
   * @param yhi with unit of domain negated
   * @return */
  // public static VisualImage create(BufferedImage bufferedImage, Show visualSet, Tensor domain, Scalar yhi) {
  //// Unit unitX = visualSet.getAxisX().getUnit();
  //// ScalarUnaryOperator suoX = UnitConvert.SI().to(unitX);
  //// Clip clipX = Clips.interval(suoX.apply(domain.Get(0)), suoX.apply(Last.of(domain)));
  // // ---
  //// Unit unitY = visualSet.getAxisY().getUnit();
  // ScalarUnaryOperator suoY = UnitConvert.SI().to(unitY);
  // Clip clipY = Clips.interval(suoY.apply(yhi.zero()), suoY.apply(yhi));
  // // ---
  // VisualImage visualImage = new VisualImage(bufferedImage, clipX, clipY);
  // visualImage.getAxisX().setLabel(visualSet.getAxisX().getLabel());
  // visualImage.getAxisY().setLabel(visualSet.getAxisY().getLabel());
  // return visualImage;
  // }
  public static Clip minMax(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .filter(FiniteScalarQ::of) //
        .collect(MinMax.toClip());
  }
}
