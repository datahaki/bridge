package ch.alpine.bridge.fig;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.pow.Sqrt;

public class GridComponent {
  public static JPanel asd(List<Show> list) {
    Scalar sqrt = Sqrt.FUNCTION.apply(RealScalar.of(list.size()));
    int cols = Round.intValueExact(sqrt);
    int rows = Ceiling.intValueExact(sqrt);
    JPanel grid = new JPanel(new GridLayout(rows, cols));
    for (Show show : list) {
      ShowComponent showComponent = new ShowComponent();
      showComponent.setShow(show);
      grid.add(showComponent);
    }
    return grid;
  }
}
