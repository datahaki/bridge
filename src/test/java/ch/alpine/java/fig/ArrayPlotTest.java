// code by GRZ Technologies SA, jph
package ch.alpine.java.fig;

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
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class ArrayPlotTest extends TestCase {
  public void testSimple() throws IOException {
    int n = 200;
    Tensor domain = Subdivide.increasing(Clips.interval(Quantity.of(10, "m"), Quantity.of(20, "m")), n - 1);
    Tensor values = domain.map(s -> Boole.of(Scalars.lessThan(Quantity.of(19, "m"), s)));
    VisualSet visualSet = new VisualSet();
    visualSet.getAxisX().setUnit(Unit.of("dm"));
    visualSet.add(domain, values);
    visualSet.setPlotLabel("Array Plot Label");
    JFreeChart jFreeChart = ArrayPlot.of( //
        visualSet, //
        ImageFormat.of(Tensors.of(values).map(ColorDataGradients.CLASSIC)));
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("arrayplot.png"), jFreeChart, 600, 200);
  }
}
