// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.itp.BSplineFunctionString;
import ch.alpine.tensor.itp.MitchellNetravaliKernel;
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
import ch.alpine.tensor.pdf.c.GammaDistribution;
import ch.alpine.tensor.pdf.c.LogNormalDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;
import ch.alpine.tensor.pdf.c.TriangularDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.BinomialDistribution;
import ch.alpine.tensor.pdf.d.DiscreteUniformDistribution;
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
import ch.alpine.tensor.sca.tri.ArcCos;
import ch.alpine.tensor.sca.tri.ArcSin;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.sca.win.WindowFunctions;
import ch.alpine.tensor.tmp.ResamplingMethods;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TimeSeriesIntegrate;

/* package */ enum ShowDemos {
  ListLinePlot0 {
    @Override
    Show create() {
      Tensor rgba = ColorDataGradients.ALPINE.queryTableRgba().orElseThrow();
      // System.out.println(Dimensions.of(rgba));
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      show.setPlotLabel("Color Data Gradient 097");
      Tensor domain = Range.of(0, rgba.length());
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
      return show;
    }
  },
  ListLinePlot1 {
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
      show.add(Plot.of(pdf_o::at, clip)).setLabel("PDF");
      show.add(Plot.of(pdf::at, clip)).setLabel("trunc. PDF");
      show.add(Plot.of(cdf::p_lessEquals, clip)).setLabel("trunc. CDF");
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
  TimeSeries_DT {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Time Series");
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT);
      timeSeries.insert(DateTime.of(2022, 11, 3, 10, 45), Quantity.of(4, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 3, 20, 35), Quantity.of(2, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 4, 8, 15), Quantity.of(1, "kW"));
      show.add(TsPlot.of(timeSeries)).setLabel("timeSeries");
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
      Showable showable = show.add(TsPlot.of(integral));
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
      show.add(TsPlot.of(TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
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
  EMPTY_NO_DATA {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Unnamed");
      return show;
    }
  },
  EMPTY_WITH_VIEW {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Unnamed");
      show.setCbb(CoordinateBoundingBox.of( //
          Clips.absolute(Quantity.of(4, "m*kg")), //
          Clips.absolute(Quantity.of(2, "s*A^-1"))));
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
  Filling1 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      show.setPlotLabel("Sine");
      show.add(Plot.filling(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2))).setLabel("sine");
      return show;
    }
  },
  Filling2 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Gamma Distributions");
      show.add(Plot.filling(PDF.of(GammaDistribution.of(1, 2))::at, Clips.positive(20))).setLabel("alpha = 1");
      show.add(Plot.filling(PDF.of(GammaDistribution.of(4, 2))::at, Clips.positive(20))).setLabel("alpha = 4");
      show.add(Plot.filling(PDF.of(GammaDistribution.of(6, 2))::at, Clips.positive(20))).setLabel("alpha = 6");
      return show;
    }
  },
  Ts_WP3 {
    @Override
    Show create() {
      Scalar mu = Quantity.of(0.3, "m*s^-1");
      Scalar sigma = Quantity.of(1, "m*s^-1/2");
      Scalar t_zero = DateTime.of(2020, 3, 4, 22, 15);
      Scalar t_fine = DateTime.of(2020, 3, 4, 22, 16);
      RandomProcess randomProcess = WienerProcess.of(mu, sigma, t_zero, Quantity.of(-3, "m"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Distribution distribution = UniformDistribution.of(t_zero, t_fine);
      RandomVariate.of(distribution, 1000).map(randomFunction::evaluate);
      Show show = new Show(ColorDataLists._001.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Offset");
      show.add(TsPlot.of(randomFunction.timeSeries())).setLabel("timeSeries");
      show.add(TsPlot.of(TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
      return show;
    }
  },
  Candlestick {
    @Override
    Show create() {
      Scalar mu = Quantity.of(-0.3e-10, "m*s^-1");
      Scalar sigma = Quantity.of(1e-3, "m*s^-1/2");
      Scalar t_zero = DateTime.of(2000, 3, 4, 22, 15);
      Scalar t_fine = DateTime.of(2020, 3, 4, 22, 16);
      RandomProcess randomProcess = WienerProcess.of(mu, sigma, t_zero, Quantity.of(-3, "m"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Distribution distribution = UniformDistribution.of(t_zero, t_fine);
      RandomVariate.of(distribution, 1000).map(randomFunction::evaluate);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      show.setPlotLabel("Candlestick Chart");
      TimeSeries timeSeries = randomFunction.timeSeries();
      show.add(CandlestickChart.of(timeSeries)).setLabel("candles");
      show.add(TsPlot.of(timeSeries)).setLabel("timeSeries");
      return show;
    }
  },
  ReImPlot0 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._097.strict());
      show.setPlotLabel("ReImPlot");
      Clip clip = Clips.absolute(4);
      show.add(ReImPlot.filling(ArcSin.FUNCTION, clip)).setLabel("arc sin");
      show.add(ReImPlot.of(ArcCos.FUNCTION, clip)).setLabel("arc cos");
      return show;
    }
  },
  Spectrogram0 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Spectrogram");
      show.add(SpectrogramDemo.create(0.32, 1.6)).setLabel("Chirp");
      return show;
    }
  },
  DensityPlot0(true) {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Density Plot");
      ScalarBinaryOperator sbo = (x, y) -> x.multiply(y);
      show.add(DensityPlot.of(sbo, CoordinateBoundingBox.of(Clips.positive(1), Clips.positive(2))));
      return show;
    }
  },
  DiscretePlot1 {
    @Override
    Show create() {
      int n = 50;
      Distribution distribution = BinomialDistribution.of(n, RationalScalar.HALF);
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      Show show = new Show();
      show.setPlotLabel(distribution.toString());
      Clip clip = Clips.positive(n);
      show.add(DiscretePlot.of(pdf::at, clip)).setLabel("PDF");
      show.add(DiscretePlot.of(cdf::p_lessEquals, clip)).setLabel("CDF");
      return show;
    }
  },
  ArrayPlot0(true) {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Array Plot");
      show.add(ArrayPlot.of(Tensors.fromString("{{1, 0, 0, 0.3}, {1, 1, 0, 0.3}, {1, 0, 1, 0.7}}")));
      return show;
    }
  },
  MatrixPlot0(true) {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Matrix Plot");
      show.add(MatrixPlot.of(Tensors.fromString("{{1, 2, 1}, {3, 0, 1}, {0, 0, -1}}")));
      return show;
    }
  },
  MatrixPlot1(true) {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Matrix Plot");
      Tensor matrix = ResourceData.of("/ch/alpine/bridge/fig/hb_west0381.csv");
      matrix = matrix.map(Clips.absoluteOne());
      show.add(MatrixPlot.of(matrix));
      return show;
    }
  },
  MatrixPlot2(true) {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Chebyshev Nodes");
      Tensor matrix = ChebyshevNodes._1.matrix(64);
      show.add(MatrixPlot.of(matrix));
      return show;
    }
  },
  DiscretePlot0 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Discrete Plot");
      show.add(DiscretePlot.of(PDF.of(BinomialDistribution.of(20, 0.3))::at, Clips.positive(20))).setLabel("0.3");
      show.add(DiscretePlot.of(PDF.of(BinomialDistribution.of(25, 0.5))::at, Clips.positive(25))).setLabel("0.5");
      return show;
    }
  },
  ImagePlot1 {
    @Override
    Show create() {
      Show show = new Show();
      show.setPlotLabel("Image Plot");
      BufferedImage bufferedImage = ResourceData.bufferedImage("/ch/alpine/bridge/io/image/album_in.jpg");
      show.add(ImagePlot.of(bufferedImage));
      return show;
    }
  },
  DateTimeY {
    @Override
    Show create() {
      Distribution distribution = UniformDistribution.of( //
          DateTime.of(1980, 3, 7, 12, 45), //
          DateTime.of(1981, 3, 7, 12, 45));
      Tensor points = RandomVariate.of(distribution, 20, 2);
      Show show = new Show();
      show.setPlotLabel("DateTime UnOp");
      show.add(ListPlot.of(points));
      return show;
    }
  },
  MultiTsPlot0 {
    @Override
    Show create() {
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethods.HOLD_VALUE_FROM_LEFT);
      Distribution dX = UniformDistribution.of( //
          DateTime.of(1980, 3, 7, 12, 45), //
          DateTime.of(1981, 3, 7, 12, 45));
      Distribution dY = LogNormalDistribution.standard();
      Tensor ofs = Tensors.vector(0, 5, 10);
      for (int i = 0; i < 20; ++i)
        timeSeries.insert(RandomVariate.of(dX), RandomVariate.of(dY, 3).add(ofs));
      Show show = new Show();
      show.setPlotLabel("MultiTs");
      show.add(MultiTsPlot.of(timeSeries, t -> t, ColorDataLists._094.strict()));
      return show;
    }
  },
  MatrixPlotDT(true) {
    @Override
    Show create() {
      Distribution dX = UniformDistribution.of( //
          DateTime.of(1980, 3, 7, 12, 45), //
          DateTime.of(1980, 5, 7, 12, 45));
      Tensor matrix = RandomVariate.of(dX, 10, 20);
      Show show = new Show();
      show.setPlotLabel("MP DateTime");
      show.add(MatrixPlot.of(matrix, ColorDataGradients.TEMPERATURE_LIGHT, false));
      return show;
    }
  },
  MatrixPlotNonSym(true) {
    @Override
    Show create() {
      Distribution dX = DiscreteUniformDistribution.of(100, 111);
      Tensor matrix = RandomVariate.of(dX, 10, 20);
      Show show = new Show();
      show.setPlotLabel("MP NonSymmetric");
      show.add(MatrixPlot.of(matrix, ColorDataGradients.CLASSIC, false));
      return show;
    }
  },
  MitchellNet0 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("MitchellNetravaliKernel");
      show.add(Plot.of(MitchellNetravaliKernel.standard(), Clips.absolute(2))).setLabel("1/3_1/3");
      show.add(Plot.of(MitchellNetravaliKernel.of(1, 1), Clips.absolute(2))).setLabel("1_1");
      return show;
    }
  },
  //
  ;

  public final boolean extra;

  private ShowDemos() {
    this(false);
  }

  private ShowDemos(boolean extra) {
    this.extra = extra;
  }

  abstract Show create();
}
