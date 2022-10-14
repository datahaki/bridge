// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.HilbertMatrix;
import ch.alpine.tensor.mat.sv.SingularValueList;
import ch.alpine.tensor.sca.exp.Log10;

/** inspired from strang's book */
public enum ScreePlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor matrix = HilbertMatrix.of(40);
    VisualSet visualSet = new VisualSet();
    Tensor values = SingularValueList.of(matrix);
    visualSet.add(Range.of(0, values.length()), values.map(Log10.FUNCTION));
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ScreePlotDemo.class.getSimpleName() + ".png"), jFreeChart, //
        new Dimension(640, 480));
  }
}
