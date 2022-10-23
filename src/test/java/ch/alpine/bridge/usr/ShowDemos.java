// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.prc.RandomFunction;
import ch.alpine.tensor.prc.WienerProcess;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.tmp.ResamplingMethods;
import ch.alpine.tensor.tmp.TimeSeries;

public enum ShowDemos {
  DEMO1 {
    @Override
    Show create() {
      Tensor domain = Subdivide.increasing(Clips.unit(), 50);
      Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
      show.add(new Plot(s -> Cos.FUNCTION.apply(s.add(s)).multiply(RealScalar.of(100)), Clips.positive(0.5))).setLabel("sine");
      return show;
    }
  },
  DEMO2 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      show.add(new Plot(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2))).setLabel("sine");
      return show;
    }
  },
  DEMO3 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in("rad");
      show.add(new Plot(s -> Cos.FUNCTION.apply(suo.apply(s)), Clips.absolute(Quantity.of(180, "deg")))).setLabel("cosine");
      return show;
    }
  },
  TS_DT {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT);
      timeSeries.insert(DateTime.of(2022, 11, 3, 10, 45), Quantity.of(4, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 3, 20, 35), Quantity.of(2, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 4, 8, 15), Quantity.of(1, "kW"));
      ScalarUnaryOperator suo = s -> (Scalar) timeSeries.evaluate(s);
      show.add(new Plot(suo, timeSeries.domain())).setLabel("timeSeries");
      return show;
    }
  },
  TS_WP {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._058.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      RandomFunction randomFunction = RandomFunction.of(WienerProcess.standard());
      show.add(new Plot(randomFunction::evaluate, Clips.unit())).setLabel("timeSeries");
      return show;
    }
  },
  LP_NOT_JOINED {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      show.add(new ListPlot(RandomVariate.of(UniformDistribution.unit(), 10, 2), false)).setLabel("timeSeries");
      show.add(new ListPlot(RandomVariate.of(UniformDistribution.unit(), 20, 2), false)).setLabel("timeSeries");
      show.add(new ListPlot(RandomVariate.of(UniformDistribution.unit(), 4, 2), true)).setLabel("timeSeries");
      return show;
    }
  };

  abstract Show create();
}
