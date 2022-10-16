// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.sca.Clips;

/* package */ enum ListPlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.increasing(Clips.unit(), 50);
    Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
    Show visualSet = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
    visualSet.setPlotLabel(ListPlot.class.getSimpleName());
    visualSet.add(domain, rgba.get(Tensor.ALL, 0)).setLabel("red");
    visualSet.add(domain, rgba.get(Tensor.ALL, 1)).setLabel("green");
    visualSet.add(domain, rgba.get(Tensor.ALL, 2)).setLabel("blue");
    Showable jFreeChart = ListPlot.of(visualSet);
    File file = HomeDirectory.Pictures(ListPlot.class.getSimpleName() + ".png");
    Show.export(file, jFreeChart, new Dimension(DemoHelper.DEMO_W, DemoHelper.DEMO_H));
  }
}
