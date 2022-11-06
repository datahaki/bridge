package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.sca.Clips;

public enum CoDaGrDemo {
  ;
  public static void main(String[] args) {
    ColorDataGradients colorDataGradients = ColorDataGradients.CLASSIC;
    Tensor rgba = colorDataGradients.queryTableRgba().orElseThrow();
    // System.out.println(Dimensions.of(rgba));
    Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
    show.setPlotLabel("Color Data Gradient Alpine");
    {
      Tensor domain = Range.of(0, rgba.length());
      show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("red");
      // show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      // show.add(ListLinePlot.of(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
    }
    {
      Tensor sampl = Subdivide.increasing(Clips.unit(), 63);
    }
    ShowDialog.of(show);
  }
}
