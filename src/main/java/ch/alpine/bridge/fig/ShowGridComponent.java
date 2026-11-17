// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.pow.Sqrt;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GraphicsGrid.html">GraphicsGrid</a> */
public enum ShowGridComponent {
  ;
  /** @param list non-empty
   * @return
   * @throws Exception if list is empty */
  public static JComponent of(List<Show> list) {
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

  public static JComponent of(Show... shows) {
    return of(List.of(shows));
  }

  public static JComponent column(List<Show> list, Dimension dimension) {
    JPanel jPanel = new ColumnPanel();
    for (Show show : list) {
      ShowComponent showComponent = new ShowComponent();
      showComponent.setShow(show);
      showComponent.setPreferredSize(dimension);
      jPanel.add(showComponent);
    }
    return new JScrollPane(jPanel);
  }

  public static List<Rectangle> rectangles(int n, Dimension size) {
    Scalar sqrt = Sqrt.FUNCTION.apply(RealScalar.of(n));
    int cols = Round.intValueExact(sqrt);
    int rows = Ceiling.intValueExact(sqrt);
    Dimension dimension = new Dimension(size.width / cols, size.height / rows);
    Tensor matrix = Tensors.matrix((i, j) -> Tensors.vector(i, j), rows, cols);
    return Flatten.of(matrix, 1).stream().map(ij -> new Rectangle(new Point( //
        ij.Get(1).number().intValue() * dimension.width, //
        ij.Get(0).number().intValue() * dimension.height), dimension)).toList();
  }
}
