// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** base class for ArrayPlot and MatrixPlot */
/* package */ final class BaseArrayPlot extends ImagePlot {
  // (ScalableImage scalableImage, CoordinateBoundingBox cbb, BarLegend barLegend, boolean flipY, Scalar aspectRatio)
  public BaseArrayPlot( //
      ScalableImage scalableImage, //
      CoordinateBoundingBox cbb, //
      BarLegend barLegend) {
    super(scalableImage, ImageResize.DEGREE_0, cbb, barLegend, true, RealScalar.ONE);
  }
}
