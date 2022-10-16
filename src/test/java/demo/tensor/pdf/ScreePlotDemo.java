// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
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
    Show visualSet = new Show();
    Tensor values = SingularValueList.of(matrix);
    visualSet.add(Range.of(0, values.length()), values.map(Log10.FUNCTION));
    Showable jFreeChart = ListPlot.of(visualSet.setJoined(true));
    Show.export(HomeDirectory.Pictures(ScreePlotDemo.class.getSimpleName() + ".png"), jFreeChart, //
        new Dimension(640, 480));
  }
}
