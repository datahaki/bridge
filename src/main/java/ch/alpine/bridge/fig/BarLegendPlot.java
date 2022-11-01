// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/* package */ abstract class BarLegendPlot extends BaseShowable {
  private final ScalarTensorFunction colorDataGradient;

  public BarLegendPlot(ScalarTensorFunction colorDataGradient) {
    this.colorDataGradient = colorDataGradient;
  }

  protected abstract Clip clip();

  @Override
  public final void tender(ShowableConfig showableConfig, Graphics graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    int width = StaticHelper.GAP * 2;
    int pix = rectangle.x + rectangle.width + 1 + StaticHelper.GAP * 2;
    graphics.drawImage(ImageFormat.of(Subdivide.decreasing(Clips.unit(), rectangle.height - 1).map(Tensors::of).map(colorDataGradient)), //
        pix, rectangle.y, width, rectangle.height, null);
    new AxisYR(ISO8601DateTimeFocus.INSTANCE).render( //
        showableConfig, //
        new Point(pix + width + StaticHelper.GAP - 2, rectangle.y), //
        rectangle.height, graphics, clip());
  }
}
