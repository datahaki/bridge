// code by gjoel, jph
package ch.alpine.java.fig;

import ch.alpine.tensor.Tensors;
import junit.framework.TestCase;

public class DatasetFactoryTest extends TestCase {
  public void testSimple() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.fromString("{{0, 0}, {1, NaN}}"));
    DatasetFactory.xySeriesCollection(visualSet);
    DatasetFactory.categoryTableXYDataset(visualSet);
    DatasetFactory.defaultCategoryDataset(visualSet);
  }
}
