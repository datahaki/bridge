// code by jph
package ch.alpine.java.fig;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensors;

public class StackedTablePlotTest {
  @Test
  public void testEmpty() {
    VisualSet visualSet = new VisualSet();
    TestHelper.draw(StackedTablePlot.of(visualSet));
  }

  @Test
  public void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.empty());
    TestHelper.draw(StackedTablePlot.of(visualSet));
  }
}
