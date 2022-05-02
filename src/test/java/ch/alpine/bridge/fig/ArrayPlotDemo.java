// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.num.Boole;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum ArrayPlotDemo {
  ;
  public static JFreeChart create() {
    int n = 200;
    Clip clipX = Clips.interval(Quantity.of(10, "m"), Quantity.of(20, "m"));
    Tensor domain = Subdivide.increasing(clipX, n - 1);
    Tensor values = domain.map(s -> Boole.of(Scalars.lessThan(Quantity.of(19, "m"), s)));
    BufferedImage bufferedImage = ImageFormat.of(Tensors.of(values).map(ColorDataGradients.CLASSIC));
    Clip clipY = Clips.interval(0, 1);
    VisualImage visualImage = new VisualImage(bufferedImage, clipX, clipY);
    visualImage.getAxisX().setUnit(Unit.of("dm"));
    visualImage.setPlotLabel("Array Plot");
    return ArrayPlot.of(visualImage);
  }

  public static JFreeChart create2() {
    int n = 1000;
    Tensor domain = Subdivide.of(0, 50, n - 1);
    Tensor values = domain.map(Scalar::zero);
    int incr = 3;
    for (int index = 0; index < domain.length(); index += incr) {
      values.set(RealScalar.ONE, index);
      incr++;
    }
    BufferedImage bufferedImage = ImageFormat.of(Tensors.of(values).map(ColorDataGradients.CLASSIC));
    Clip clipX = Clips.interval(0, 50);
    Clip clipY = Clips.interval(0, 1);
    VisualImage visualImage = new VisualImage(bufferedImage, clipX, clipY);
    visualImage.setPlotLabel("Array Plot 2");
    return ArrayPlot.of(visualImage);
  }

  public static void main(String[] args) throws IOException {
    {
      JFreeChart jFreeChart = create();
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ArrayPlotDemo.class.getSimpleName() + ".png"), jFreeChart, 600, 200);
    }
    {
      JFreeChart jFreeChart = create2();
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ArrayPlotDemo.class.getSimpleName() + "2.png"), jFreeChart, 600, 300);
    }
  }
}
