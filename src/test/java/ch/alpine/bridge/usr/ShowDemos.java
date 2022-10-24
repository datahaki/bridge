// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
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

/* package */ enum ShowDemos {
  DEMO1 {
    @Override
    Show create() {
      Tensor domain = Subdivide.increasing(Clips.unit(), 50);
      Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel("Color Data Gradient");
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
      show.setPlotLabel("Sine");
      show.add(new Plot(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2))).setLabel("sine");
      return show;
    }
  },
  DEMO3 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Cosine");
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in("rad");
      show.add(new Plot(s -> Cos.FUNCTION.apply(suo.apply(s)), Clips.absolute(Quantity.of(180, "deg")))).setLabel("cosine");
      return show;
    }
  },
  TS_DT {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Time Series");
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT);
      timeSeries.insert(DateTime.of(2022, 11, 3, 10, 45), Quantity.of(4, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 3, 20, 35), Quantity.of(2, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 4, 8, 15), Quantity.of(1, "kW"));
      show.add(new Plot(timeSeries)).setLabel("timeSeries");
      return show;
    }
  },
  TS_WP {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._058.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process");
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
  },
  EMPTY {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Unnamed");
      return show;
    }
  },
  ARRAY_PLOT0 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Array Plot");
      show.add(new ArrayPlot(Tensors.fromString("{{1, 0, 0, 0.3}, {1, 1, 0, 0.3}, {1, 0, 1, 0.7}}")));
      return show;
    }
  },
  SPECTROGRAM0 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Spectrogram");
      show.add(SpectrogramDemo.create(0.32, 1.6));
      return show;
    }
  }
  //
  ;

  abstract Show create();
}
