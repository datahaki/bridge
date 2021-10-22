// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.ImageFormat;

public enum ArrayPlot {
  ;
  public static JFreeChart of(Tensor image, Scalar dt) {
    return of(image, dt, "");
  }

  /** @param image tensor of rank 2 (grayscale) or 3 (rgba)
   * @param dt spacing between two samples
   * @return */
  public static JFreeChart of(Tensor image, Scalar dt, String plotLabel) {
    BufferedImage bufferedImage = ImageFormat.of(image);
    VisualSet visualSet = new VisualSet();
    Scalar xhi = dt.multiply(RealScalar.of(bufferedImage.getWidth()));
    Scalar yhi = RealScalar.of(image.length());
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
