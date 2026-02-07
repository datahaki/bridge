// code by jph
package showcase.fig;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;

class ColorDataGradientsDemo implements ShowProvider {
  @Override
  public Show getShow() {
    ColorDataGradients colorDataGradients = ColorDataGradients.CLASSIC;
    Tensor rgba = colorDataGradients.queryTableRgba().orElseThrow();
    Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
    show.setPlotLabel("ColorDataGradient " + colorDataGradients);
    {
      Tensor domain = Range.of(0, rgba.length());
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
    }
    return show;
  }

  static void main() {
    new ColorDataGradientsDemo().run();
  }
}
