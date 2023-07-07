// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.fft.HaarWaveletTransform;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.tri.Sin;

/* package */ enum PeriodogramDemo {
  ;
  private static Scalar _f(Scalar n) {
    Scalar x1 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.2), n, Pi.VALUE));
    Scalar x2 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.5), n, Pi.VALUE));
    return x1.add(x1).add(x2);
  }

  public static Show create2() {
    int n = 128;
    Tensor noised = RandomVariate.of(UniformDistribution.of(-0.1, 0.1), n);
    Tensor domain = Range.of(0, n);
    Tensor signal = Tensors.vector(i -> _f(RealScalar.of(i)), n).add(noised);
    Show show = new Show();
    show.setPlotLabel(Periodogram.class.getSimpleName() + " and " + HaarWaveletTransform.class.getSimpleName());
    {
      Tensor points = Transpose.of(Tensors.of(domain, signal));
      Showable showable = show.add(Periodogram.of(points));
      showable.setLabel(Periodogram.class.getSimpleName());
    }
    {
      Tensor result = HaarWaveletTransform.FORWARD.transform(signal);
      Tensor points = Transpose.of(Tensors.of(domain.multiply(RealScalar.of(0.5 / 128.)), result));
      Showable showable = show.add(ListLinePlot.of(points));
      showable.setLabel(HaarWaveletTransform.class.getSimpleName());
    }
    return show;
  }

  public static void main(String[] args) {
    Show show = create2();
    ShowDialog.of(show);
  }
}
