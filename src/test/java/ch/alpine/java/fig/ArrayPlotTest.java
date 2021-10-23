// code by GRZ Technologies SA, jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.num.Boole;
import ch.alpine.tensor.opt.nd.Box;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class ArrayPlotTest extends TestCase {
  public void testSimple() throws IOException {
    int n = 200;
    Clip clipX = Clips.interval(Quantity.of(10, "m"), Quantity.of(20, "m"));
    Tensor domain = Subdivide.increasing(clipX, n - 1);
    Tensor values = domain.map(s -> Boole.of(Scalars.lessThan(Quantity.of(19, "m"), s)));
    BufferedImage bufferedImage = ImageFormat.of(Tensors.of(values).map(ColorDataGradients.CLASSIC));
    Box box = Box.of(Tensors.of(clipX.min(), RealScalar.ZERO), Tensors.of(clipX.max(), RealScalar.ONE));
    VisualArray visualArray = new VisualArray(box, bufferedImage);
    visualArray.getAxisX().setUnit(Unit.of("dm"));
    visualArray.setPlotLabel("Array Plot Label");
    JFreeChart jFreeChart = ArrayPlot.of(visualArray);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("arrayplot.png"), jFreeChart, 600, 200);
  }
}
