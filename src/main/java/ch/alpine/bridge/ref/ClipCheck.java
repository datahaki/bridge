// code by jph
package ch.alpine.bridge.ref;

import java.util.function.Predicate;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;

/* package */ class ClipCheck implements Predicate<Scalar> {
  private final Clip clip;
  private final Predicate<Scalar> compatible;
  private final ScalarUnaryOperator convert;

  public ClipCheck(Clip clip) {
    this.clip = clip;
    Unit unit = QuantityUnit.of(clip);
    compatible = CompatibleUnitQ.SI().with(unit);
    convert = UnitConvert.SI().to(unit);
  }

  public Clip clip() {
    return clip;
  }

  @Override
  public boolean test(Scalar scalar) {
    return FiniteScalarQ.of(scalar) //
        && compatible.test(scalar) //
        && clip.isInside(convert.apply(scalar));
  }
}