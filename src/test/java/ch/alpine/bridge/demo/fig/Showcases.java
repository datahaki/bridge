// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import ch.alpine.bridge.fig.Meshgrid;
import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowOption;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.plt.ArrayPlot;
import ch.alpine.bridge.fig.plt.ArrowPlot;
import ch.alpine.bridge.fig.plt.CandlestickChart;
import ch.alpine.bridge.fig.plt.DensityPlot;
import ch.alpine.bridge.fig.plt.DiscretePlot;
import ch.alpine.bridge.fig.plt.ImagePlot;
import ch.alpine.bridge.fig.plt.ListLinePlot;
import ch.alpine.bridge.fig.plt.ListPlot;
import ch.alpine.bridge.fig.plt.MatrixPlot;
import ch.alpine.bridge.fig.plt.MultiTsPlot;
import ch.alpine.bridge.fig.plt.ParametricPlot;
import ch.alpine.bridge.fig.plt.Periodogram;
import ch.alpine.bridge.fig.plt.Plot;
import ch.alpine.bridge.fig.plt.PolygonPlot;
import ch.alpine.bridge.fig.plt.ReImPlot;
import ch.alpine.bridge.fig.plt.ReliefPlot;
import ch.alpine.bridge.fig.plt.Spectrogram;
import ch.alpine.bridge.fig.plt.StringPlot;
import ch.alpine.bridge.fig.plt.StringPlot.StringItem;
import ch.alpine.bridge.fig.plt.TsPlot;
import ch.alpine.bridge.fig.plt.VectorPlot;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.ComplexScalar;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.ext.ResourceData;
import ch.alpine.tensor.fft.SpectrogramArrays;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.Import;
import ch.alpine.tensor.itp.Interpolation;
import ch.alpine.tensor.itp.LanczosInterpolation;
import ch.alpine.tensor.itp.MitchellNetravaliKernel;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.lie.rot.Cross;
import ch.alpine.tensor.mat.GaussianMatrix;
import ch.alpine.tensor.mat.HilbertMatrix;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.mat.sv.SingularValueList;
import ch.alpine.tensor.nrm.Vector2NormSquared;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.num.Softplus;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.c.GammaDistribution;
import ch.alpine.tensor.pdf.c.LogNormalDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
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
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.Arg;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Im;
import ch.alpine.tensor.sca.Ramp;
import ch.alpine.tensor.sca.SawtoothWave;
import ch.alpine.tensor.sca.SquareWave;
import ch.alpine.tensor.sca.TriangleWave;
import ch.alpine.tensor.sca.erf.Erfc;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.exp.LogisticSigmoid;
import ch.alpine.tensor.sca.gam.Gamma;
import ch.alpine.tensor.sca.ply.Chebyshev;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;
import ch.alpine.tensor.sca.pow.Power;
import ch.alpine.tensor.sca.pow.Sqrt;
import ch.alpine.tensor.sca.tri.ArcCos;
import ch.alpine.tensor.sca.tri.ArcSin;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.tmp.ResamplingMethod;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TimeSeriesIntegrate;

public enum Showcases implements ShowProvider {
  Relief {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("ArrowPlot");
      Showable showable = ReliefPlot.of(GaussianMatrix.of(40), CoordinateBoundingBox.of(Clips.unit(), Clips.unit()), ColorDataGradients.ALPINE);
      show.add(showable);
      return show;
    }
  },
  Relief2 {
    @Override
    public Show getShow() {
      int resx = 100;
      Clip clip = Clips.absolute(3);
      CoordinateBoundingBox cbb = CoordinateBoundingBox.of(clip, clip);
      ScalarBinaryOperator sbo = (x, y) -> Sin.FUNCTION.apply(Vector2NormSquared.of(Tensors.of(x, y)));
      Tensor matrix = Meshgrid.of(cbb, resx).image(sbo);
      Show showR = new Show();
      showR.setPlotLabel("ReliefPlot");
      Showable showable = ReliefPlot.of(matrix, cbb, ColorDataGradients.DENSITY);
      showR.add(showable);
      return showR;
    }
  },
  Arrows {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("ArrowPlot");
      show.add(new ArrowPlot(Tensors.vector(1, 0), Tensors.vector(3, 2)));
      show.add(new ArrowPlot(Tensors.vector(2, 3), Tensors.vector(-1, -2)));
      show.setAspectRatioOne();
      return show;
    }
  },
  Axes {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("PolygonPlot");
      show.add(PolygonPlot.of(CirclePoints.of(8), PlotOption.FILL)).setAlpha(32);
      show.add(PolygonPlot.of(CirclePoints.of(16)));
      show.add(ListPlot.of(Tensors.fromString("{{-1,1},{1,-1}}")));
      show.setAspectRatioOne();
      return show;
    }
  },
  VectorPlot2 {
    @Override
    public Show getShow() {
      TensorUnaryOperator tuo = xy -> Cross.of(xy).multiply(Quantity.of(1, "s^-1"));
      CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.positive(Quantity.of(2.5, "m")), Clips.positive(Quantity.of(2.5, "m")));
      Show show = new Show();
      // DensityPlot densityPlot = DensityPlot.of(sbo, cbb, ColorDataGradients.HUE);
      VectorPlot vectorPlot = VectorPlot.of(tuo, cbb);
      vectorPlot.setStroke(new BasicStroke(2f));
      vectorPlot.setLabel("Cross w/ Units");
      show.add(vectorPlot);
      show.setPlotLabel("VectorPlot");
      return show;
    }
  },
  DensityJulia {
    @Override
    public Show getShow() {
      Scalar MAX = RealScalar.of(50);
      int MAX_ITERATIONS = 10;
      Scalar c = ComplexScalar.of(1.1, 0.5);
      ScalarBinaryOperator sbo = (re, im) -> {
        Scalar z = ComplexScalar.of(re, im);
        for (int count = 0; count < MAX_ITERATIONS; ++count) {
          z = Sin.FUNCTION.apply(z).multiply(c);
          if (Scalars.lessThan(MAX, Abs.FUNCTION.apply(Im.FUNCTION.apply(z))))
            return DoubleScalar.INDETERMINATE;
        }
        return Arg.FUNCTION.apply(z);
      };
      CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.interval(-2.5, +4.5), Clips.interval(-2.3, +2.3));
      Show show = new Show();
      DensityPlot densityPlot = DensityPlot.of(sbo, cbb, ColorDataGradients.HUE);
      densityPlot.setPlotPoints(200);
      show.add(densityPlot);
      show.setPlotLabel("Density Plot Julia Set");
      return show;
    }
  },
  DensityGamma {
    @Override
    public Show getShow() {
      Show show = new Show(); // use RGB for line color
      ScalarBinaryOperator sbo = (re, im) -> Arg.FUNCTION.apply(Gamma.FUNCTION.apply(ComplexScalar.of(re, im)));
      show.add(DensityPlot.of(sbo, //
          CoordinateBoundingBox.of(Clips.absolute(3), Clips.absolute(3)), //
          ColorDataGradients.HUE));
      show.setPlotLabel("Arg Gamma");
      return show;
    }
  },
  TextsPlot0 {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._109.strict()); // use RGB for line color
      List<StringItem> list = new LinkedList<>();
      list.add(StringItem.of(Tensors.vector(1, 2), "12"));
      list.add(StringItem.of(Tensors.vector(3, 2), "32"));
      list.add(StringItem.of(Tensors.vector(2, 3), "23"));
      list.add(StringItem.of(Tensors.vector(4, 4), "text"));
      show.add(StringPlot.of(list));
      show.setPlotLabel("StringPlot");
      return show;
    }
  },
  ListLinePlot1 {
    @Override
    public Show getShow() {
      ColorDataGradient f = ColorDataGradients.CLASSIC;
      Clip clip = Clips.unit();
      Show show = new Show(ColorDataLists._109.strict()); // use RGB for line color
      show.setPlotLabel("Color Data Gradient Classic");
      show.add(Plot.of(s -> f.apply(s).Get(0), clip, PlotOption.STRICT)).setLabel("red");
      show.add(Plot.of(s -> f.apply(s).Get(1), clip, PlotOption.STRICT)).setLabel("green");
      show.add(Plot.of(s -> f.apply(s).Get(2), clip, PlotOption.STRICT)).setLabel("blue");
      return show;
    }
  },
  PlotCosine {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._098.strict());
      show.setPlotLabel("Cosine");
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in("rad");
      Showable showable = show.add(Plot.of(s -> Cos.FUNCTION.apply(suo.apply(s)), Clips.absolute(Quantity.of(180, "deg"))));
      showable.setLabel("cosine");
      showable.setStroke(new BasicStroke(0.5f));
      return show;
    }
  },
  ListLinePlotQuantity {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("ListLinePlot");
      show.add(ListLinePlot.of(Tensors.fromString("{{2[m],3[s]}, {3[m],0[s]}, {4[m],3[s]}, {5[m],1[s]}}"))).setLabel("first");
      show.add(ListLinePlot.of(Tensors.fromString("{{3[m],2[s]}, {4[m],2.5[s]}, {5[m],2[s]}}"))).setLabel("second");
      return show;
    }
  },
  ListPlots {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("With Infinity");
      Tensor points = Tensors.fromString("{{0,0}, {0.2, Infinity}, {0.3, 0.3}}");
      show.add(ListPlot.of(points));
      show.add(ListLinePlot.of(points));
      return show;
    }
  },
  WaveFunctions0 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Wave Functions");
      ScalarUnaryOperator[] suos = { //
          SawtoothWave.FUNCTION, //
          SquareWave.FUNCTION, //
          TriangleWave.FUNCTION };
      for (ScalarUnaryOperator suo : suos)
        show.add(Plot.of(suo, Clips.absolute(2)));
      return show;
    }
  },
  PlotAndList {
    @Override
    public Show getShow() {
      Tensor matrix = HilbertMatrix.of(40);
      Show show = new Show();
      show.setPlotLabel("Plot & ListPlot");
      Tensor values = SingularValueList.of(matrix);
      show.add(ListPlot.of(Range.of(0, values.length()), values.maps(Log10.FUNCTION))).setLabel("singular values");
      Clip clip = Clips.absolute(5);
      show.add(Plot.of(Erfc.FUNCTION, clip)).setLabel("Erfc");
      return show;
    }
  },
  TruncatedDistribution0 {
    @Override
    public Show getShow() {
      Distribution original = NormalDistribution.standard();
      Distribution distribution = TruncatedDistribution.of(original, Clips.interval(-1, 1.5));
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      PDF pdf_o = PDF.of(original);
      Show show = new Show();
      show.setPlotLabel("Truncated Distribution");
      Clip clip = Clips.interval(-3, 3);
      show.add(Plot.of(pdf_o::at, clip, PlotOption.FILL)).setLabel("orig. PDF");
      show.add(Plot.of(pdf::at, clip, PlotOption.FILL)).setLabel("trunc. PDF");
      show.add(Plot.of(cdf::p_lessEquals, clip)).setLabel("trunc. CDF");
      return show;
    }
  },
  TimeSeries_DT {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Time Series");
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethod.HOLD_VALUE_FROM_LEFT);
      timeSeries.insert(DateTime.of(2022, 11, 3, 10, 45), Quantity.of(4, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 3, 20, 35), Quantity.of(2, "kW"));
      timeSeries.insert(DateTime.of(2022, 11, 4, 8, 15), Quantity.of(1, "kW"));
      show.add(TsPlot.of(timeSeries)).setLabel("timeSeries");
      return show;
    }
  },
  TS_WP1 {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._058.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Integral");
      RandomFunction randomFunction = RandomFunction.of(WienerProcess.of(3, 1));
      Tensor samples = RandomVariate.of(UniformDistribution.of(Clips.unit()), 100);
      samples.maps(randomFunction::evaluate); // for
                                              // integral
      show.add(Plot.of(randomFunction::evaluate, Clips.unit(), PlotOption.STRICT)).setLabel("timeSeries");
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
    public Show getShow() {
      Scalar mu = Quantity.of(1, "m*s^-1");
      Scalar sigma = Quantity.of(0.2, "m*s^-1/2");
      RandomProcess randomProcess = WienerProcess.of(mu, sigma);
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Show show = new Show(ColorDataLists._001.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Drift");
      show.add(Plot.of(randomFunction::evaluate, Clips.positive(Quantity.of(5, "s")))).setLabel("timeSeries");
      show.add(TsPlot.of(TimeSeries.empty(ResamplingMethod.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
      return show;
    }
  },
  TS_PP0 {
    @Override
    public Show getShow() {
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
    public Show getShow() {
      int max = 6;
      Tensor domain = Subdivide.of(-1., 1., 30);
      Show show = new Show();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = ClenshawChebyshev.of(UnitVector.of(d + 1, d));
        ScalarUnaryOperator su2 = Chebyshev.T.of(d);
        show.add(ListLinePlot.of(domain, domain.maps(suo).subtract(domain.maps(su2)))).setLabel("" + d);
        // show.add(Plot.of(s->suo.apply(s).subtract(su2.apply(s)), Clips.absoluteOne())).setLabel(""+d);
      }
      return show;
    }
  },
  POLY3 {
    @Override
    public Show getShow() {
      int n = 7 + 7;
      ScalarUnaryOperator suo = x -> Sin.FUNCTION.apply(x.multiply(x).negate().add(x));
      Tensor domain = Subdivide.of(-1, 1, 100);
      Show show = new Show();
      show.setPlotLabel("Clenshaw Chebyshev");
      for (ChebyshevNodes chebyshevNodes : ChebyshevNodes.values()) {
        Tensor coeffs = LinearSolve.of(chebyshevNodes.matrix(n), chebyshevNodes.of(n).maps(suo));
        // System.out.println(Pretty.of(coeffs.map(Round._3)));
        Tensor error = domain.maps(ClenshawChebyshev.of(coeffs)).subtract(domain.maps(suo));
        Showable showable = show.add(ListLinePlot.of(domain, error));
        showable.setLabel(chebyshevNodes.name());
      }
      return show;
    }
  },
  LP_NOT_JOINED {
    @Override
    public Show getShow() {
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
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Unnamed");
      return show;
    }
  },
  EMPTY_WITH_VIEW {
    @Override
    public Show getShow() {
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
    public Show getShow() {
      Scalar f0 = Pi.TWO.multiply(RealScalar.of(697));
      Scalar f1 = Pi.TWO.multiply(RealScalar.of(1209));
      ScalarUnaryOperator suo = t -> Sin.FUNCTION.apply(f0.multiply(t)).add(Sin.FUNCTION.apply(f1.multiply(t)));
      Tensor domain = Subdivide.of(0.0, 0.3, 2400);
      Tensor signal = domain.maps(suo);
      Tensor points = Transpose.of(Tensors.of(domain, signal));
      Show show = new Show();
      show.setPlotLabel("Periodogram");
      show.add(Periodogram.of(points));
      return show;
    }
  },
  LP_ZERO_HEIGHT {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("FlatlineX");
      show.add(ListPlot.of(Tensors.fromString("{{0,1}, {10,1}}")));
      return show;
    }
  },
  LP_ZERO_WIDTH {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("FlatlineY");
      show.add(ListPlot.of(Tensors.fromString("{{0,1}, {0,10}}")));
      return show;
    }
  },
  LP_ZERO_HEIGHT_UNIT {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("FlatlineX Quantity");
      show.add(ListPlot.of(Tensors.fromString("{{0[m],1[s]}, {10[m],1[s]}}")));
      return show;
    }
  },
  LP_ZERO_WIDTH_UNIT {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("FlatlineY Quantity");
      show.add(ListPlot.of(Tensors.fromString("{{0[m],1[s]}, {0[m],10[s]}}")));
      return show;
    }
  },
  Filling1 {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      show.setPlotLabel("Sine");
      show.add(Plot.of(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2), PlotOption.FILL)).setLabel("sine");
      return show;
    }
  },
  Filling2 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Gamma Distributions");
      show.add(Plot.of(PDF.of(GammaDistribution.of(1, 2))::at, Clips.positive(20), PlotOption.FILL)).setLabel("alpha = 1");
      show.add(Plot.of(PDF.of(GammaDistribution.of(4, 2))::at, Clips.positive(20), PlotOption.FILL)).setLabel("alpha = 4");
      show.add(Plot.of(PDF.of(GammaDistribution.of(6, 2))::at, Clips.positive(20), PlotOption.FILL)).setLabel("alpha = 6");
      return show;
    }
  },
  Ts_WP3 {
    @Override
    public Show getShow() {
      Scalar mu = Quantity.of(0.3, "m*s^-1");
      Scalar sigma = Quantity.of(1, "m*s^-1/2");
      Scalar t_zero = DateTime.of(2020, 3, 4, 22, 15);
      Scalar t_fine = DateTime.of(2020, 3, 4, 22, 16);
      RandomProcess randomProcess = WienerProcess.of(mu, sigma, t_zero, Quantity.of(-3, "m"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Distribution distribution = UniformDistribution.of(t_zero, t_fine);
      RandomVariate.of(distribution, 1000).maps(randomFunction::evaluate);
      Show show = new Show(ColorDataLists._001.strict().deriveWithAlpha(192));
      show.setPlotLabel("Wiener Process with Offset");
      show.add(TsPlot.of(randomFunction.timeSeries())).setLabel("timeSeries");
      show.add(TsPlot.of(TimeSeries.empty(ResamplingMethod.HOLD_VALUE_FROM_LEFT))).setLabel("empty ts");
      return show;
    }
  },
  Candlestick {
    @Override
    public Show getShow() {
      Scalar mu = Quantity.of(-0.3e-10, "m*s^-1");
      Scalar sigma = Quantity.of(1e-3, "m*s^-1/2");
      Scalar t_zero = DateTime.of(2000, 3, 4, 22, 15);
      Scalar t_fine = DateTime.of(2020, 3, 4, 22, 16);
      RandomProcess randomProcess = WienerProcess.of(mu, sigma, t_zero, Quantity.of(-3, "m"));
      RandomFunction randomFunction = RandomFunction.of(randomProcess);
      Distribution distribution = UniformDistribution.of(t_zero, t_fine);
      RandomVariate.of(distribution, 1000).maps(randomFunction::evaluate);
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
    public Show getShow() {
      Show show = new Show(ColorDataLists._097.strict());
      show.setPlotLabel("ReImPlot");
      Clip clip = Clips.absolute(4);
      show.add(ReImPlot.of(ArcSin.FUNCTION, clip)).setLabel("arc sin");
      show.add(ReImPlot.of(ArcCos.FUNCTION, clip)).setLabel("arc cos");
      return show;
    }
  },
  SpectrogramLin {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Spectrogram");
      Tensor signal = Subdivide.of(0.0, 100.0, 1000).maps(t -> Sin.FUNCTION.apply(t.multiply(t)));
      Showable showable = Spectrogram.of(SpectrogramArrays.FOURIER.operator(), signal, Quantity.of(8000, "s^-1"));
      show.add(showable);
      return show;
    }
  },
  DensityPlot1(true) {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Density Plot");
      ScalarBinaryOperator sbo = (x, y) -> Im.FUNCTION.apply(Sqrt.FUNCTION.apply(Power.of(ComplexScalar.of(x, y), 3)));
      show.add(DensityPlot.of(sbo, CoordinateBoundingBox.of(Clips.absolute(2), Clips.absolute(2))));
      return show;
    }
  },
  MatrixPlot0(true) {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Matrix Plot");
      show.add(MatrixPlot.of(Tensors.fromString("{{1, 2, 1}, {3, 0, 1}, {0, 0, -1}}"), true));
      return show;
    }
  },
  MatrixPlot1(true) {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Matrix Plot");
      Tensor matrix = Import.of("ch/alpine/bridge/fig/hb_west0381.csv");
      matrix = matrix.maps(Clips.absoluteOne());
      show.add(MatrixPlot.of(matrix));
      return show;
    }
  },
  MatrixPlot2(true) {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Chebyshev Nodes");
      Tensor matrix = ChebyshevNodes._1.matrix(64);
      show.add(MatrixPlot.of(matrix));
      return show;
    }
  },
  DiscretePlot0 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Discrete Plot");
      show.add(DiscretePlot.of(PDF.of(BinomialDistribution.of(20, 0.3))::at, Clips.positive(20))).setLabel("0.3");
      show.add(DiscretePlot.of(PDF.of(BinomialDistribution.of(25, 0.5))::at, Clips.positive(25))).setLabel("0.5");
      return show;
    }
  },
  DiscretePlot1 {
    @Override
    public Show getShow() {
      int n = 50;
      Distribution distribution = BinomialDistribution.of(n, Rational.HALF);
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
  DiscretePlot2 {
    @Override
    public Show getShow() {
      Distribution original = PoissonDistribution.of(7);
      Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
      PDF pdf = PDF.of(distribution);
      CDF cdf = CDF.of(distribution);
      PDF pdf_o = PDF.of(original);
      Show show = new Show();
      show.setPlotLabel("Truncated Poisson Distribution[7]");
      Clip clip = Clips.positive(12);
      show.add(DiscretePlot.of(pdf::at, clip));
      show.add(DiscretePlot.of(cdf::p_lessEquals, clip));
      show.add(DiscretePlot.of(pdf_o::at, clip));
      return show;
    }
  },
  ImagePlot3 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Image Plot BICUBIC");
      BufferedImage bufferedImage = ResourceData.bufferedImage("ch/alpine/bridge/io/image/album_in.jpg");
      show.add(ImagePlot.of(bufferedImage, ImageResize.DEGREE_3));
      show.set(ShowOption.AXIS_X, false);
      return show;
    }
  },
  ImagePlot1_ {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Image Plot BILINEAR");
      BufferedImage bufferedImage = ResourceData.bufferedImage("ch/alpine/bridge/io/image/album_in.jpg");
      show.add(ImagePlot.of(bufferedImage, ImageResize.DEGREE_1));
      show.set(ShowOption.AXIS_Y, false);
      return show;
    }
  },
  ImagePlot1 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Image Plot NEAREST");
      BufferedImage bufferedImage = ResourceData.bufferedImage("ch/alpine/bridge/io/image/album_in.jpg");
      show.add(ImagePlot.of(bufferedImage, ImageResize.DEGREE_0));
      return show;
    }
  },
  DateTimeY {
    @Override
    public Show getShow() {
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
    public Show getShow() {
      TimeSeries timeSeries = TimeSeries.empty(ResamplingMethod.HOLD_VALUE_FROM_LEFT);
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
    public Show getShow() {
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
    public Show getShow() {
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
    public Show getShow() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("MitchellNetravaliKernel");
      show.add(Plot.of(MitchellNetravaliKernel.standard(), Clips.absolute(2))).setLabel("1/3_1/3");
      show.add(Plot.of(MitchellNetravaliKernel.of(1, 1), Clips.absolute(2))).setLabel("1_1");
      show.setAspectRatioOne();
      return show;
    }
  },
  ParametricPlot0 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("ParametricPlot");
      show.add(ParametricPlot.of(s -> Tensors.of( //
          Sin.FUNCTION.apply(s).multiply(Rational.HALF), //
          Sin.FUNCTION.apply(s.add(s))), Clips.positive(Pi.TWO)));
      show.setAspectRatioOne();
      return show;
    }
  },
  ArrayPlot0(true) {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Array Plot");
      show.add(ArrayPlot.of(Tensors.fromString("{{1, 0, 0, 0.3}, {1, 1, 0, 0.3}, {1, 0, 1, 0.7}}")));
      show.setAspectRatio(RealScalar.ONE, Rational.HALF);
      return show;
    }
  },
  Softplus1 {
    @Override
    public Show getShow() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      show.setPlotLabel("Ramps");
      Clip clip = Clips.absolute(5);
      show.add(Plot.of(Ramp.FUNCTION, clip)).setLabel("Ramp");
      show.add(Plot.of(Softplus.FUNCTION, clip)).setLabel("Softplus");
      show.add(Plot.of(LogisticSigmoid.FUNCTION, clip)).setLabel("LogisticSigmoid");
      show.add(Plot.of(s -> //
      LogisticSigmoid.FUNCTION.apply(s).multiply(Ramp.FUNCTION.apply(s)), clip)).setLabel("LogisticSigmoid*Ramp");
      show.setAspectRatioOne();
      return show;
    }
  },
  Cepstrogram0Re {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Cepstrogram");
      // Tensor signal = Subdivide.of(0.0, 100.0, 10000).maps(t->Sin.FUNCTION.apply(t.multiply(t)));
      Tensor signal = Tensor.of(IntStream.range(0, 10000) //
          .mapToObj(i -> Rational.of(i, 100).add(Rational.of(i * i, 1000_000))) //
          .map(SawtoothWave.FUNCTION));
      show.add(Spectrogram.of(SpectrogramArrays.REAL.operator(), signal, RealScalar.ONE));
      return show;
    }
  },
  ImagePlot2 {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("Image Plot With Units w/o gridlines");
      BufferedImage bufferedImage = ResourceData.bufferedImage("ch/alpine/bridge/io/image/album_in.jpg");
      CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.positive(Quantity.of(3, "m")), Clips.absolute(Quantity.of(4, "s")));
      show.add(ImagePlot.of(bufferedImage, cbb));
      Tensor tensor = Tensors.fromString("{{1[m],2[s]},{1.6[m],3[s]},{2.3[m],-1.3[s]}}");
      show.add(ListLinePlot.of(tensor));
      show.add(ListPlot.of(tensor));
      show.set(ShowOption.GRID, false);
      return show;
    }
  },
  LanczosKernel {
    @Override
    public Show getShow() {
      Show show = new Show();
      show.setPlotLabel("LanczosKernel");
      Tensor tensor = Tensors.fromString("{{0,0,0,0}, {0,1,2,0}, {0,0,-1,0}, {0,0,0,0}, {0,0,0,0}}");
      CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.positive(3), Clips.positive(4));
      Interpolation interpolation = LanczosInterpolation.of(tensor);
      DensityPlot densityPlot = DensityPlot.of( //
          (x, y) -> (Scalar) interpolation.get(Tensors.of(y, x)), cbb, ColorDataGradients.CLASSIC);
      show.add(densityPlot);
      return show;
    }
  },;

  public final boolean extra;

  private Showcases() {
    this(false);
  }

  private Showcases(boolean extra) {
    this.extra = extra;
  }
}
