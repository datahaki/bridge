// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

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
    VisualImage visualArray = new VisualImage(clipX, clipY, bufferedImage);
    visualArray.getAxisX().setUnit(Unit.of("dm"));
    visualArray.setPlotLabel("Array Plot");
    return ArrayPlot.of(visualArray);
  }

  public static void main(String[] args) throws IOException {
    JFreeChart jFreeChart = create();
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ArrayPlotDemo.class.getSimpleName() + ".png"), jFreeChart, 600, 200);
  }
}
