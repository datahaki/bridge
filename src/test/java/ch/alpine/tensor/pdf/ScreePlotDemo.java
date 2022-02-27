// code by jph
package ch.alpine.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.HilbertMatrix;
import ch.alpine.tensor.mat.sv.SingularValueList;
import ch.alpine.tensor.sca.Log10;

/** inspired from strang's book */
public enum ScreePlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor matrix = HilbertMatrix.of(40);
    VisualSet visualSet = new VisualSet();
    Tensor values = SingularValueList.of(matrix);
    visualSet.add(Range.of(0, values.length()), values.map(Log10.FUNCTION));
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ScreePlotDemo.class.getSimpleName() + ".png"), jFreeChart, 640, 480);
  }
}
