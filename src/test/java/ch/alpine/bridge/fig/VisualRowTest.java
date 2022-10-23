// code by gjoel, jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;

class VisualRowTest {
  @Test
  void testConstructors() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor points = Transpose.of(Tensors.of(domain, values));
    Show visualSet = new Show();
    Showable row1 = visualSet.add(new ListPlot(points));
    // row1.getStroke();
    Showable row2 = visualSet.add(new ListPlot(domain, values));
    // assertEquals(row1.points(), row2.points());
    // assertEquals(visualSet.visualRows().size(), 2);
    // row1.setStroke(new BasicStroke(2f));
    // row1.getStroke();
  }

  @Test
  void testDateTime() {
    Show visualSet = new Show();
    Distribution distribution = NormalDistribution.of(DateTime.now(), Quantity.of(3, "h"));
    Tensor points = RandomVariate.of(distribution, 10, 2);
    visualSet.add(new ListPlot(points));
  }

  @Test
  void testFailNull() {
    assertThrows(Exception.class, () -> new Show(null));
  }

  @Test
  void testPointNonMatrix() {
    assertThrows(Exception.class, () -> new ListPlot(Tensors.vector(1, 2, 3, 4)));
    assertThrows(Exception.class, () -> new ListPlot(RealScalar.ZERO));
  }
}
