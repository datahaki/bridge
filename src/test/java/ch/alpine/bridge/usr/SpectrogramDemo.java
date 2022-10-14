// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.Spectrogram;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.win.DirichletWindow;

public enum SpectrogramDemo {
  ;
  public static JFreeChart create() {
    return create(0.32, 1.6);
  }

  public static JFreeChart create(double lo, double hi) {
    ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
        0, //
        800, //
        2800).multiply(Pi.VALUE));
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    Tensor signal = domain.map(polynomial).map(Cos.FUNCTION).map(s -> Quantity.of(s, "m"));
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel("Spectrogram");
    visualSet.add(domain.map(s -> Quantity.of(s, "s")), signal);
    visualSet.getAxisX().setLabel("time");
    visualSet.getAxisY().setLabel("frequency");
    ColorDataGradient colorDataGradient = ColorDataGradients.SUNSET_REVERSED;
    return Spectrogram.of(visualSet, DirichletWindow.FUNCTION, colorDataGradient);
  }

  public static void main(String[] args) throws IOException {
    JFreeChart jFreeChart = create(0.0, 1.65);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(Spectrogram.class.getSimpleName() + ".png"), //
        jFreeChart, //
        new Dimension(DemoHelper.DEMO_W, DemoHelper.DEMO_H));
  }
}
