// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Cos;

/* package */ enum ListPlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.increasing(Clips.unit(), 50);
    Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
    Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
    show.setCbb(CoordinateBoundingBox.of(Clips.unit(), Clips.interval(-2, 2)));
    show.setPlotLabel(ListPlot.class.getSimpleName());
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
    show.add(new Plot(s -> Cos.FUNCTION.apply(s.add(s)), Clips.positive(0.5))).setLabel("sine");
    File file = HomeDirectory.Pictures(ListPlot.class.getSimpleName() + ".png");
    show.export(file, new Dimension(DemoHelper.DEMO_W, DemoHelper.DEMO_H));
  }
}
