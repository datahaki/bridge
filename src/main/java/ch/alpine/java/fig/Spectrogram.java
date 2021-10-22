// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.win.DirichletWindow;

public enum Spectrogram {
  ;
  public static JFreeChart of(Tensor signal, Scalar dt, Function<Scalar, ? extends Tensor> function) {
    return of(signal, dt, function, "");
  }

  /** @param signal vector
   * @param dt spacing between two samples
   * @param function
   * @return */
  public static JFreeChart of(Tensor signal, Scalar dt, Function<Scalar, ? extends Tensor> function, String plotLabel) {
    BufferedImage bufferedImage = ImageFormat.of(ch.alpine.tensor.fft.Spectrogram.of(signal, DirichletWindow.FUNCTION, function));
    VisualSet visualSet = new VisualSet();
    Scalar xhi = dt.multiply(RealScalar.of(bufferedImage.getWidth()));
    Scalar yhi = dt.reciprocal().multiply(RationalScalar.HALF);
    Tensor box = Tensors.of( //
        Tensors.of(xhi.zero(), yhi.zero()), //
        Tensors.of(xhi, yhi));
    visualSet.add(box);
    Plot plot = new BufferedImagePlot(bufferedImage, visualSet);
    JFreeChart jFreeChart = new JFreeChart(plotLabel, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
