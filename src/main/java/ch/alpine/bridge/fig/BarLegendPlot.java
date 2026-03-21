// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Clips;

public abstract class BarLegendPlot extends BaseShowable {
  protected final CoordinateBoundingBox cbb;
  private boolean aspectRatioOneHint = true;

  public BarLegendPlot(CoordinateBoundingBox cbb) {
    this.cbb = Objects.requireNonNull(cbb);
  }

  /** @return may also return null */
  protected abstract BarLegend barLegend();

  @Override // from Showable
  public final Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  public final boolean getAspectRatioOneHint() {
    Unit unit0 = QuantityUnit.of(cbb.clip(0).width());
    Unit unit1 = QuantityUnit.of(cbb.clip(1).width());
    return aspectRatioOneHint //
        && unit0.equals(unit1);
  }

  public final void setAspectRatioOne(boolean hint) {
    aspectRatioOneHint = hint;
  }

  @Override
  public final void tender(ShowableConfig showableConfig, Graphics2D graphics) {
    BarLegend barLegend = barLegend();
    if (Objects.nonNull(barLegend)) {
      Rectangle rectangle = showableConfig.rectangle();
      int width = StaticHelper.GAP * 2;
      int pix = rectangle.x + rectangle.width + 1 + StaticHelper.GAP * 2;
      graphics.drawImage(ImageFormat.of(Subdivide.decreasing(Clips.unit(), rectangle.height - 1).maps(Tensors::of).maps(barLegend.colorDataGradient())), //
          pix, rectangle.y, width, rectangle.height, null);
      ConfBase confBase = new ConfDecr(rectangle.y, rectangle.height, barLegend.clip());
      AxisOptions axisOptions = new AxisOptions();
      axisOptions.set(AxisOption.TICK, true);
      new AxisYR(confBase, axisOptions).render( //
          showableConfig, //
          new Point(pix + width + StaticHelper.GAP - 2, rectangle.y), //
          graphics);
    }
  }
}
