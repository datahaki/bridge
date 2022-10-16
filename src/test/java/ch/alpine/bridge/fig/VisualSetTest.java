// code by gjoel, jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class VisualSetTest {
  @Test
  void testConstructors() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor points = Transpose.of(Tensors.of(domain, values));
    Show set2 = new Show();
    VisualRow row2 = set2.add(domain, values);
    row2.setLabel("row2");
    VisualRow row3 = set2.add(points);
    row3.setLabel("row3");
    assertEquals(set2.visualRows().size(), 2);
    Show set1 = new Show();
    assertEquals(set1.visualRows().size(), 0);
  }

  @Test
  void testAdd() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 5);
    Show visualSet = new Show();
    VisualRow row1 = visualSet.add(domain, values1);
    VisualRow row2 = visualSet.add(domain, values2);
    assertEquals(Dimensions.of(row1.points()), Dimensions.of(row2.points()));
  }

  @Test
  void testSetRowLabel() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 5);
    Show set = new Show();
    VisualRow row1 = set.add(domain, values1);
    VisualRow row2 = set.add(domain, values2);
    row1.setLabel("row 1");
    row2.setLabel("row 2");
    assertEquals(set.getVisualRow(0).getLabelString(), "row 1");
    assertEquals(set.getVisualRow(1).getLabelString(), "row 2");
  }

  @Test
  void testEmptyPass(@TempDir File tempDir) throws IOException {
    Show visualSet = new Show();
    visualSet.add(Tensors.empty());
    Showable jFreeChart = ListPlot.of(visualSet);
    File file = new File(tempDir, "file.png");
    assertFalse(file.exists());
    Show.export(file, jFreeChart, new Dimension(100, 100));
    assertTrue(file.isFile());
    assertTrue(file.canWrite());
  }

  @Test
  void testFailScalar() {
    Show visualSet = new Show();
    assertThrows(Exception.class, () -> visualSet.add(RealScalar.ZERO, RealScalar.ONE));
  }

  @Test
  void testFailVector() {
    Show visualSet = new Show();
    assertThrows(Exception.class, () -> visualSet.add(Tensors.vector(1, 2, 3, 4), Tensors.vector(1, 2, 3, 4, 5)));
  }

  @Test
  void testFailUnstructured() {
    Show visualSet = new Show();
    assertThrows(Exception.class, () -> visualSet.add(Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}, {3}}")));
    assertThrows(Exception.class, () -> visualSet.add(Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}, 4}")));
    assertThrows(Exception.class, () -> visualSet.add(Tensors.fromString("{{1, 2, 3}, {3, 4, 2}, {5, 6, 3}, {3, 5, 3}}")));
  }
}
