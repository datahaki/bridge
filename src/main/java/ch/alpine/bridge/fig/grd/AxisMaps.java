// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.bridge.fig.tic.Ticks;
import ch.alpine.bridge.fig.tic.TicksDec;
import ch.alpine.bridge.fig.tic.TicksLog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

public enum AxisMaps implements AxisMap {
  LIN {
    @Override
    public Scalar model2range(Scalar model) {
      return model;
    }

    @Override
    public Scalar range2model(Scalar range) {
      return range;
    }

    @Override
    public TickOption tickOption() {
      return TickOption.MODEL;
    }

    @Override
    public String format(Scalar model) {
      return TickToString.formatValue(model);
    }

    @Override
    public Ticks ticks() {
      return TicksDec.DENSE;
    }
  },
  LOG {
    @Override
    public Scalar model2range(Scalar model) {
      return Log.FUNCTION.apply(model);
    }

    @Override
    public Scalar range2model(Scalar range) {
      return Exp.FUNCTION.apply(range);
    }

    @Override
    public TickOption tickOption() {
      return TickOption.RANGE;
    }

    @Override
    public String format(Scalar range) {
      return "exp " + TickToString.formatValue(range);
    }

    @Override
    public Ticks ticks() {
      return TicksLog.INSTANCE;
    }
  },
  LOG10 {
    @Override
    public Scalar model2range(Scalar model) {
      return Log10.FUNCTION.apply(model);
    }

    @Override
    public Scalar range2model(Scalar range) {
      return Power.of(10, range);
    }

    @Override
    public TickOption tickOption() {
      return TickOption.RANGE;
    }

    @Override
    public String format(Scalar range) {
      return TickToString.formatValue(range2model(range));
    }

    @Override
    public Ticks ticks() {
      return TicksLog.INSTANCE;
    }
  },
  dB {
    private static final Scalar _20 = RealScalar.of(20.0);

    @Override
    public Scalar model2range(Scalar model) {
      return Log10.FUNCTION.apply(model).multiply(_20);
    }

    @Override
    public Scalar range2model(Scalar range) {
      return Power.of(10, range.divide(_20));
    }

    @Override
    public TickOption tickOption() {
      return TickOption.RANGE;
    }

    @Override
    public String format(Scalar range) {
      return TickToString.formatValue(range2model(range)) + " dB";
    }

    @Override
    public Ticks ticks() {
      return TicksDec.DENSE;
    }
  }
}
