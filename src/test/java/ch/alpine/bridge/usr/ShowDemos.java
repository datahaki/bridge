// code by jph
package ch.alpine.bridge.usr;

import java.awt.BasicStroke;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Periodogram;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.prc.PoissonProcess;
import ch.alpine.tensor.prc.RandomFunction;
import ch.alpine.tensor.prc.RandomProcess;
import ch.alpine.tensor.prc.WienerProcess;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.tmp.ResamplingMethods;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TimeSeriesIntegrate;

/* package */ enum ShowDemos {
  DEMO1 {
    @Override
    Show create() {
      Tensor domain = Subdivide.increasing(Clips.unit(), 50);
      Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel("Color Data Gradient");
      show.add(ListPlot.of(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(ListPlot.of(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(ListPlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
      show.add(Plot.of(s -> Cos.FUNCTION.apply(s.add(s)).multiply(RealScalar.of(100)), Clips.positive(0.5))).setLabel("sine");
      return show;
    }
  },
  DEMO2 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      show.setPlotLabel("Sine");
      show.add(Plot.of(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2))).setLabel("sine");
      return show;
    }
  },
  DEMO3 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Cosine");
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in("rad");
      Showable showable = show.add(Plot.of(s -> Cos.FUNCTION.apply(suo.apply(s)), Clips.absolute(Quantity.of(180, "deg"))));
      showable.setLabel("cosine");
      showable.setStroke(new BasicStroke(0.5f));
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
      show.add(Plot.of(timeSeries)).setLabel("timeSeries");
      return show;
    }
  },
  TS_WP1 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._058.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Integral");
      RandomFunction randomFunction = RandomFunction.of(WienerProcess.standard());
      Tensor samples = RandomVariate.of(UniformDistribution.of(Clips.unit()), 100);
      samples.map(randomFunction::evaluate); // for integral
      show.add(Plot.of(randomFunction::evaluate, Clips.unit())).setLabel("timeSeries");
      TimeSeries timeSeries = randomFunction.timeSeries();
      TimeSeries integral = TimeSeriesIntegrate.of(timeSeries);
      Showable showable = show.add(Plot.of(integral));
      showable.setStroke(new BasicStroke(0.6f));
      showable.setLabel("integral");
      return show;
    }
  },
  TS_WP2 {
    @Override
    Show create() {
      Scalar mu = Quantity.of(1, "m*s^-1");
      Scalar sigma = Quantity.of(0.2, "m*s^-1/2");
      RandomProcess randomProcess = WienerProcess.of(mu, sigma);
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Show show = new Show(ColorDataLists._001.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Drift");
      show.add(Plot.of(randomFunction::evaluate, Clips.positive(Quantity.of(5, "s")))).setLabel("timeSeries");
      return show;
    }
  },
  TS_PP0 {
    @Override
    Show create() {
      RandomProcess randomProcess = PoissonProcess.of(Quantity.of(3.4, "s^-1"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Show show = new Show(ColorDataLists._003.strict().deriveWithAlpha(192));
      show.setPlotLabel("Poisson Process");
      show.add(Plot.of(randomFunction::evaluate, Clips.positive(Quantity.of(10, "s")))).setLabel("timeSeries");
      return show;
    }
  },
  LP_NOT_JOINED {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      show.add(ListPlot.of(RandomVariate.of(UniformDistribution.unit(), 10, 2), false)).setLabel("timeSeries");
      show.add(ListPlot.of(RandomVariate.of(UniformDistribution.unit(), 20, 2), false)).setLabel("timeSeries");
      show.add(ListPlot.of(RandomVariate.of(UniformDistribution.unit(), 4, 2), true)).setLabel("timeSeries");
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
  PERIODOGRAM {
    @Override
    Show create() {
      Scalar f0 = Pi.TWO.multiply(RealScalar.of(697));
      Scalar f1 = Pi.TWO.multiply(RealScalar.of(1209));
      ScalarUnaryOperator suo = t -> Sin.FUNCTION.apply(f0.multiply(t)).add(Sin.FUNCTION.apply(f1.multiply(t)));
      Tensor domain = Subdivide.of(0.0, 0.3, 2400);
      Tensor signal = domain.map(suo);
      Tensor points = Transpose.of(Tensors.of(domain, signal));
      Show show = new Show();
      show.setPlotLabel("Periodogram");
      show.add(Periodogram.of(points));
      return show;
    }
  },
  ARRAY_PLOT0 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Array Plot");
      show.add(ArrayPlot.of(Tensors.fromString("{{1, 0, 0, 0.3}, {1, 1, 0, 0.3}, {1, 0, 1, 0.7}}")));
      return show;
    }
  },
  ARRAY_PLOT1 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Chebyshev Nodes");
      Tensor matrix = ChebyshevNodes._1.matrix(64);
      // VisualImage visualImage = new VisualImage(matrix);
      // visualImage.setPlotLabel("ArrayPlot");
      Tensor tensor = Rescale.of(matrix).map(ColorDataGradients.ALPINE);
      show.add(ArrayPlot.of(ImageFormat.of(tensor), CoordinateBoundingBox.of(Clips.unit(), Clips.unit())));
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
