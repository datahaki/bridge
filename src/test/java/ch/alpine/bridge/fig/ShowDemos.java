// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.itp.BSplineFunctionString;
import ch.alpine.tensor.mat.HilbertMatrix;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.mat.sv.SingularValueList;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;
import ch.alpine.tensor.pdf.c.TriangularDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.BinomialDistribution;
import ch.alpine.tensor.pdf.d.PoissonDistribution;
import ch.alpine.tensor.prc.PoissonProcess;
import ch.alpine.tensor.prc.RandomFunction;
import ch.alpine.tensor.prc.RandomProcess;
import ch.alpine.tensor.prc.WienerProcess;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.erf.Erfc;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.ply.Chebyshev;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.sca.win.WindowFunctions;
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
      show.setPlotLabel("Color Data Gradient 097");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
      show.add(Plot.of(s -> Cos.FUNCTION.apply(s.add(s)).multiply(RealScalar.of(100)), Clips.positive(0.5))).setLabel("sine");
      show.add(ListLinePlot.of(Tensors.empty())).setLabel("empty");
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
  DEMO4 {
    @Override
    Show create() {
      Show show = new Show();
      show.add(ListLinePlot.of(Tensors.fromString("{{2[m],3[s]}, {3[m],0[s]}, {4[m],3[s]}, {5[m],1[s]}}"))).setLabel("first");
      show.add(ListLinePlot.of(Tensors.fromString("{{3[m],2[s]}, {4[m],2.5[s]}, {5[m],2[s]}}"))).setLabel("second");
      return show;
    }
  },
  DEMO5 {
    @Override
    Show create() {
      WindowFunctions[] smoothingKernels = new WindowFunctions[] { //
          WindowFunctions.GAUSSIAN, //
          WindowFunctions.HAMMING, //
          WindowFunctions.BLACKMAN, //
          WindowFunctions.NUTTALL, //
      };
      Show show = new Show();
      show.setPlotLabel("Window Functions");
      for (WindowFunctions windowFunctions : smoothingKernels) {
        Showable showable = show.add(Plot.of(windowFunctions.get(), Clips.absolute(0.5)));
        showable.setLabel(windowFunctions.name());
      }
      Tensor points = Tensors.fromString("{{0,0}, {0.2, Infinity}, {0.3, 0.3}}");
      show.add(ListPlot.of(points));
      show.add(ListLinePlot.of(points));
      return show;
    }
  },
  SVD0 {
    @Override
    Show create() {
      Tensor matrix = HilbertMatrix.of(40);
      Show show = new Show();
      Tensor values = SingularValueList.of(matrix);
      show.add(ListPlot.of(Range.of(0, values.length()), values.map(Log10.FUNCTION))).setLabel("singular values");
      Clip clip = Clips.absolute(5);
      show.add(Plot.of(Erfc.FUNCTION, clip)).setLabel("Erfc");
      return show;
    }
  },
  DISTR0 {
    @Override
    Show create() {
      int n = 50;
      Distribution distribution = BinomialDistribution.of(n, RationalScalar.HALF);
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      Show show = new Show();
      show.setPlotLabel(distribution.toString());
      Tensor domain = Range.of(0, n + 1);
      show.add(ListPlot.of(domain, domain.map(pdf::at))).setLabel("PDF");
      show.add(ListPlot.of(domain, domain.map(cdf::p_lessEquals))).setLabel("CDF");
      return show;
    }
  },
  DISTR1 {
    @Override
    Show create() {
      Distribution distribution = TrapezoidalDistribution.of(0.5, 1.5, 1.5, 2.5);
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel("Trapezoidal Distribution");
      Clip clip = Clips.interval(0, 4);
      show.add(Plot.of(pdf::at, clip));
      show.add(Plot.of(cdf::p_lessEquals, clip));
      Tensor sequence = Tensors.vector(0, 0, 1, 1);
      // Tensor domain = Subdivide.of(0, sequence.length() - 1, 100);
      ScalarTensorFunction sto = BSplineFunctionString.of(2, sequence);
      ScalarUnaryOperator suo = s -> (Scalar) sto.apply(s);
      show.add(Plot.of(suo, Clips.interval(0, 3)));
      return show;
    }
  },
  DISTR2 {
    @Override
    Show create() {
      Distribution original = PoissonDistribution.of(7);
      Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      PDF pdf_o = PDF.of(original);
      Show show = new Show();
      show.setPlotLabel("Truncated Poisson Distribution[7]");
      Tensor domain = Range.of(0, 12);
      show.add(ListPlot.of(pdf::at, domain));
      show.add(ListPlot.of(cdf::p_lessEquals, domain));
      show.add(ListPlot.of(pdf_o::at, domain));
      return show;
    }
  },
  DISTR3 {
    @Override
    Show create() {
      Distribution original = NormalDistribution.standard();
      Distribution distribution = TruncatedDistribution.of(original, Clips.interval(-1, 2.5));
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      PDF pdf_o = PDF.of(original);
      Show show = new Show();
      show.setPlotLabel("Truncated Distribution");
      Clip clip = Clips.interval(-3, 3);
      show.add(Plot.of(pdf::at, clip));
      show.add(Plot.of(cdf::p_lessEquals, clip));
      show.add(Plot.of(pdf_o::at, clip));
      return show;
    }
  },
  DISTR4 {
    @Override
    Show create() {
      Distribution original = NormalDistribution.standard();
      Distribution distribution = TruncatedDistribution.of(original, Clips.interval(-1, 2.5));
      InverseCDF inverseCDF = InverseCDF.of(distribution);
      Show show = new Show();
      show.setPlotLabel("Truncated Normal Distribution");
      show.add(Plot.of(inverseCDF::quantile, Clips.unit()));
      return show;
    }
  },
  DISTR5 {
    @Override
    Show create() {
      Distribution dist1 = NormalDistribution.of(2, 0.5);
      Distribution dist2 = TriangularDistribution.with(2, 0.5);
      Clip clip = Clips.interval(-3 + 2, 3 + 2);
      Show show = new Show();
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      show.add(Plot.of(pdf1::at, clip));
      show.add(Plot.of(pdf2::at, clip));
      return show;
    }
  },
  DISTR6 {
    @Override
    Show create() {
      Distribution dist1 = NormalDistribution.of(2, 1.2);
      Distribution dist2 = TrapezoidalDistribution.with(2, 1.2, 2.4);
      Clip clip = Clips.interval(-3 + 2, 3 + 2);
      Show show = new Show();
      show.setPlotLabel("Here");
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      show.add(Plot.of(pdf1::at, clip));
      show.add(Plot.of(pdf2::at, clip));
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
      RandomFunction randomFunction = RandomFunction.of(WienerProcess.of(3, 1));
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
      show.add(Plot.of(TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
      return show;
    }
  },
  TS_WP3 {
    @Override
    Show create() {
      Scalar mu = Quantity.of(1, "m*s^-1");
      Scalar sigma = Quantity.of(1, "m*s^-1/2");
      Scalar t_zero = DateTime.of(2020, 3, 4, 22, 15);
      Scalar t_fine = DateTime.of(2020, 3, 4, 22, 16);
      RandomProcess randomProcess = WienerProcess.of(mu, sigma, t_zero, Quantity.of(-3, "m"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Show show = new Show(ColorDataLists._001.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Offset");
      show.add(Plot.of(randomFunction::evaluate, Clips.interval(t_zero, t_fine))).setLabel("timeSeries");
      show.add(Plot.of(TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
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
  POLY0 {
    @Override
    Show create() {
      int max = 6;
      Tensor domain = Subdivide.of(-1., 1., 30);
      Show show = new Show();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = ClenshawChebyshev.of(UnitVector.of(d + 1, d));
        ScalarUnaryOperator su2 = Chebyshev.T.of(d);
        show.add(ListLinePlot.of(domain, domain.map(suo).subtract(domain.map(su2)))).setLabel("" + d);
        // show.add(Plot.of(s->suo.apply(s).subtract(su2.apply(s)), Clips.absoluteOne())).setLabel(""+d);
      }
      return show;
    }
  },
  POLY1 {
    @Override
    Show create() {
      int max = 6;
      Show show = new Show();
      show.setPlotLabel("Chebyshev Polynomials T");
      for (int d = 0; d < max; ++d)
        show.add(Plot.of(Chebyshev.T.of(d), Clips.absolute(1))).setLabel("" + d);
      return show;
    }
  },
  POLY2 {
    @Override
    Show create() {
      int max = 6;
      Show show = new Show();
      show.setPlotLabel("Chebyshev Polynomials U");
      for (int d = 0; d < max; ++d)
        show.add(Plot.of(Chebyshev.U.of(d), Clips.absolute(1))).setLabel("" + d);
      return show;
    }
  },
  POLY3 {
    @Override
    Show create() {
      int n = 7 + 7;
      ScalarUnaryOperator suo = x -> Sin.FUNCTION.apply(x.multiply(x).negate().add(x));
      Tensor domain = Subdivide.of(-1, 1, 100);
      Show show = new Show();
      show.setPlotLabel("Clenshaw Chebyshev");
      for (ChebyshevNodes chebyshevNodes : ChebyshevNodes.values()) {
        Tensor coeffs = LinearSolve.of(chebyshevNodes.matrix(n), chebyshevNodes.of(n).map(suo));
        // System.out.println(Pretty.of(coeffs.map(Round._3)));
        Tensor error = domain.map(ClenshawChebyshev.of(coeffs)).subtract(domain.map(suo));
        Showable showable = show.add(ListLinePlot.of(domain, error));
        showable.setLabel(chebyshevNodes.name());
      }
      return show;
    }
  },
  LP_NOT_JOINED {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      Distribution distribution = UniformDistribution.unit();
      show.add(ListPlot.of( //
          RandomVariate.of(distribution, 10), //
          RandomVariate.of(distribution, 10))).setLabel("random 10");
      show.add(ListPlot.of(RandomVariate.of(UniformDistribution.unit(), 20, 2))).setLabel("random 20");
      show.add(ListLinePlot.of(RandomVariate.of(UniformDistribution.unit(), 4, 2))).setLabel("random 4");
      show.add(ListPlot.of(Tensors.empty())).setLabel("empty");
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
      show.add(SpectrogramDemo.create(0.32, 1.6)).setLabel("Chirp");
      return show;
    }
  },
  LP_ZERO_HEIGHT {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("FlatlineX");
      show.add(ListPlot.of(Tensors.fromString("{{0,1}, {10,1}}")));
      return show;
    }
  },
  LP_ZERO_WIDTH {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("FlatlineY");
      show.add(ListPlot.of(Tensors.fromString("{{0,1}, {0,10}}")));
      return show;
    }
  },
  LP_ZERO_HEIGHT_UNIT {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("FlatlineX Quantity");
      show.add(ListPlot.of(Tensors.fromString("{{0[m],1[s]}, {10[m],1[s]}}")));
      return show;
    }
  },
  LP_ZERO_WIDTH_UNIT {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("FlatlineY Quantity");
      show.add(ListPlot.of(Tensors.fromString("{{0[m],1[s]}, {0[m],10[s]}}")));
      return show;
    }
  },
  //
  ;

  abstract Show create();
}
