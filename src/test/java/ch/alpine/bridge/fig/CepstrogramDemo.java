// code by jph
package ch.alpine.bridge.fig;

import java.util.stream.IntStream;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.fft.CepstrogramArray;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.sca.SawtoothWave;

public enum CepstrogramDemo {
  ;
  public static Showable create(SpectrogramArray xtrogramArray) {
    Tensor signal = Tensor.of(IntStream.range(0, 10000) //
        .mapToObj(i -> RationalScalar.of(i, 100).add(RationalScalar.of(i * i, 1000_000))) //
        .map(SawtoothWave.INSTANCE));
    return Cepstrogram.of(xtrogramArray, signal, RealScalar.ONE);
  }

  public static void main(String[] args) {
    Show show = new Show();
    show.add(create(CepstrogramArray.Real));
    ShowDialog.of(show);
  }
}
