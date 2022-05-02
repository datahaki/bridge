// code by gjoel, jph
package ch.alpine.bridge.fig;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensors;

class DatasetFactoryTest {
  @Test
  public void testSimple() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.fromString("{{0, 0}, {1, NaN}}"));
    DatasetFactory.xySeriesCollection(visualSet);
    DatasetFactory.categoryTableXYDataset(visualSet);
    DatasetFactory.defaultCategoryDataset(visualSet, s -> "val=" + s);
  }
}
