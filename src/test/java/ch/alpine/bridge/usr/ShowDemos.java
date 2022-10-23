// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;

public enum ShowDemos {
  DEMO1 {
    @Override
    Show create() {
      Tensor domain = Subdivide.increasing(Clips.unit(), 50);
      Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
      Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
      // show.setCbb ( CoordinateBoundingBox.of(Clips.unit(), Clips.interval(-2, 2)));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
      show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
      show.add(new Plot(s -> Cos.FUNCTION.apply(s.add(s)).multiply(RealScalar.of(100)), Clips.positive(0.5))).setLabel("sine");
      return show;
    }
  },
  DEMO2 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
      // show.setCbb ( CoordinateBoundingBox.of(Clips.unit(), Clips.interval(-2, 2)));
      show.setPlotLabel(ListPlot.class.getSimpleName());
      show.add(new Plot(s -> Sin.FUNCTION.apply(s).multiply(Quantity.of(3, "A")), Clips.absolute(2))).setLabel("sine");
//      show.add(new Plot(s -> Cos.FUNCTION.apply(s), Clips.absolute(2))).setLabel("cosine");
      return show;
    }
  },
  DEMO3 {
    @Override
    Show create() {
      Show show = new Show(ColorDataLists._098.strict().deriveWithAlpha(192));
      // show.setCbb ( CoordinateBoundingBox.of(Clips.unit(), Clips.interval(-2, 2)));
      show.setPlotLabel(ListPlot.class.getSimpleName());
//      show.add(new Plot(s -> Sin.FUNCTION.apply(s), Clips.absolute(2))).setLabel("sine");
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in("rad");
      show.add(new Plot(s -> Cos.FUNCTION.apply(suo.apply(s)), Clips.absolute(Quantity.of(180, "deg")))).setLabel("cosine");
      return show;
    }
  },
  ;

  abstract Show create();
}
