// code by gjoel, jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.BasicStroke;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class VisualRowTest {
  @Test
  public void testConstructors() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor points = Transpose.of(Tensors.of(domain, values));
    VisualSet visualSet = new VisualSet();
    VisualRow row1 = visualSet.add(points);
    row1.getStroke();
    VisualRow row2 = visualSet.add(domain, values);
    assertEquals(row1.points(), row2.points());
    assertEquals(visualSet.visualRows().size(), 2);
    row1.setStroke(new BasicStroke(2f));
    row1.getStroke();
    row1.setAutoSort(false);
  }

  @Test
  public void testFailNull() {
    assertThrows(Exception.class, () -> new VisualSet(null));
  }

  @Test
  public void testPointNonMatrix() {
    VisualSet visualSet = new VisualSet();
    assertThrows(Exception.class, () -> visualSet.add(Tensors.vector(1, 2, 3, 4)));
    assertThrows(Exception.class, () -> visualSet.add(RealScalar.ZERO));
  }
}