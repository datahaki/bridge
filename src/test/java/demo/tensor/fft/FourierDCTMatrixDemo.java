// code by jph
package demo.tensor.fft;

import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.fft.FourierDCTMatrix;

public enum FourierDCTMatrixDemo {
  ;
  public static void main(String[] args) throws IOException {
    for (FourierDCTMatrix fourierDCTMatrix : FourierDCTMatrix.values()) {
      Tensor matrix = fourierDCTMatrix.of(32);
      // System.out.println(Pretty.of(matrix.map(Round._3)));
      JFreeChart jFreeChart = ArrayPlot.of(matrix);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures( //
          FourierDCTMatrixDemo.class.getSimpleName() + fourierDCTMatrix.ordinal() + ".png"), jFreeChart, //
          400, 400);
    }
  }
}
