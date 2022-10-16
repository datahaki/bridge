// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Histogram;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;

public enum HistogramDemo {
  ;
  public static void main(String[] args) throws IOException {
    Show visualSet = new Show();
    visualSet.add(Tensors.fromString("{{2[m],3[s]}, {3[m],0[s]}, {4[m],3[s]}, {5[m],1[s]}}")).setLabel("first");
    visualSet.add(Tensors.fromString("{{3[m],2[s]}, {4[m],2.5[s]}, {5[m],2[s]}}")).setLabel("second");
    visualSet.setPlotLabel(Histogram.class.getSimpleName());
    Showable jFreeChart = Histogram.of(visualSet);
    Show.export( //
        HomeDirectory.Pictures(Histogram.class.getSimpleName() + ".png"), //
        jFreeChart, //
        new Dimension(DemoHelper.DEMO_W, //
            DemoHelper.DEMO_H));
  }
}
