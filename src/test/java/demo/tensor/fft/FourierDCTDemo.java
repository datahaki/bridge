// code by jph
package demo.tensor.fft;

import java.io.IOException;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.fft.FourierDCT;

public enum FourierDCTDemo {
  ;
  public static void main(String[] args) throws IOException {
    for (FourierDCT fourierDCT : FourierDCT.values()) {
      Tensor matrix = fourierDCT.matrix(32);
      // System.out.println(Pretty.of(matrix.map(Round._3)));
      // Showable jFreeChart = ArrayPlot.of(matrix);
      // Show.export(HomeDirectory.Pictures( //
      // FourierDCTDemo.class.getSimpleName() + fourierDCT.ordinal() + ".png"), jFreeChart, //
      // new Dimension(400, 400));
    }
  }
}
