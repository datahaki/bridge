// code by jph
package ch.alpine.java.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ComparableLabelTest {
  @Test
  public void testSimple() {
    ComparableLabel comparableLabel1 = new ComparableLabel(3);
    ComparableLabel comparableLabel2 = new ComparableLabel(5);
    assertEquals(Integer.compare(3, 5), comparableLabel1.compareTo(comparableLabel2));
    assertEquals(Integer.compare(0, 0), comparableLabel1.compareTo(comparableLabel1));
  }
}
