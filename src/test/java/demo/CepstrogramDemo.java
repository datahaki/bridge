// code by jph
package demo;

import java.awt.Dimension;
import java.io.IOException;
import java.util.stream.IntStream;

import ch.alpine.bridge.fig.Cepstrogram;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.fft.CepstrogramArray;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.sca.SawtoothWave;

public enum CepstrogramDemo {
  ;
  public static Showable create(SpectrogramArray xtrogramArray) {
    Tensor signal = Tensor.of(IntStream.range(0, 10000) //
        .mapToObj(i -> RationalScalar.of(i, 100).add(RationalScalar.of(i * i, 1000_000))) //
        .map(SawtoothWave.FUNCTION));
    return Cepstrogram.of(xtrogramArray, signal, RealScalar.ONE);
  }

  static void main() throws IOException {
    Show show = new Show();
    show.add(create(CepstrogramArray.Real));
    ShowDialog.of(show);
    show.export(HomeDirectory.Pictures("some.png"), new Dimension(600, 400));
    show.export(HomeDirectory.Pictures("some.jpg"), new Dimension(600, 400));
  }
}
