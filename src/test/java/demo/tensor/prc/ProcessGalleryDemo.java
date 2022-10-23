// code by jph
package demo.tensor.prc;

import java.io.IOException;

import ch.alpine.tensor.prc.PoissonProcess;
import ch.alpine.tensor.prc.RandomProcess;
import ch.alpine.tensor.prc.WhiteNoiseProcess;

/* package */ enum ProcessGalleryDemo {
  ;
  private static void export(RandomProcess randomProcess) throws IOException {
    // RandomFunction randomFunction = RandomFunction.of(randomProcess);
    // randomFunction.evaluate(RealScalar.of(20));
    // Show show = new Show();
    //
    // show.add(randomFunction.timeSeries());
    //
    // show.export( //
    // HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
    // new Dimension(640, 480));
  }

  private static void exportChaotic() throws IOException {
    // Scalar mu = Quantity.of(1, "m*s^-1");
    // Scalar sigma = Quantity.of(2, "m*s^-1/2");
    // RandomProcess randomProcess = WienerProcess.of(mu, sigma);
    // RandomFunction randomFunction = RandomFunction.of(randomProcess);
    // RandomVariate.of(UniformDistribution.of(Clips.positive(Quantity.of(10, "s"))), 1000).stream() //
    // .map(Scalar.class::cast) //
    // .forEach(randomFunction::evaluate);
    // {
    // Show show = new Show();
    // VisualRow visualRow = show.add(randomFunction.timeSeries());
    // visualRow.setLabel("wiener process");
    // Showable jFreeChart = ListPlot.of(show);
    // Show.export( //
    // HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
    // jFreeChart, new Dimension(640, 480));
    // }
    // {
    // TimeSeries integral = TimeSeriesIntegrate.of(randomFunction.timeSeries());
    // Show visualSet = new Show();
    // VisualRow visualRow = visualSet.add(integral);
    // visualRow.setLabel("wiener process");
    // Showable jFreeChart = ListPlot.of(visualSet);
    // Show.export( //
    // HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + "_i.png"), //
    // jFreeChart, new Dimension(640, 480));
    // }
  }

  public static void main(String[] args) throws IOException {
    // export(BinomialProcess.of(0.3));
    // export(BernoulliProcess.of(0.3));
    export(PoissonProcess.of(10));
    export(WhiteNoiseProcess.instance());
    exportChaotic();
  }
}
