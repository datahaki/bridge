// code by jph
package ch.alpine.java.fig;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UniformDistribution;
import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ListPlotTest extends TestCase {
  public void testEmpty() {
    VisualSet visualSet = new VisualSet();
    ListPlot.of(visualSet, true);
  }

  public void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6));
    TestHelper.draw(ListPlot.of(visualSet, true));
    TestHelper.draw(ListPlot.of(visualSet, false));
  }

  private static final ScalarUnaryOperator suoX = s -> Quantity.of(s, "s");
  private static final ScalarUnaryOperator suoY = s -> Quantity.of(s, "m");

  public void testUnitsX() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5).map(suoX), Tensors.vector(2, 2.2, -1.6));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(0, 2, 5).map(suoX), Tensors.vector(1, 2, 1.6));
    visualSet.add(Tensors.empty());
    TestHelper.draw(ListPlot.of(visualSet, true));
  }

  public void testUnitsY() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6).map(suoY));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(3, 5), Tensors.vector(1, 2.6).map(suoY));
    TestHelper.draw(ListPlot.of(visualSet, true));
  }

  public void testAlreadyLarge() {
    VisualSet visualSet = new VisualSet();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    visualSet.add(points);
    ListPlot.of(visualSet);
  }
}
