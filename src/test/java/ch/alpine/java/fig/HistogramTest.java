// code by jph
package ch.alpine.java.fig;

import ch.alpine.tensor.Tensors;
import junit.framework.TestCase;

public class HistogramTest extends TestCase {
  public void testEmpty() {
    VisualSet visualSet = new VisualSet();
    TestHelper.draw(Histogram.of(visualSet));
  }

  public void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.empty());
    TestHelper.draw(Histogram.of(visualSet));
  }
}
