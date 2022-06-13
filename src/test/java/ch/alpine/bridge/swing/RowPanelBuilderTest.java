// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RowPanelBuilderTest {
  @Test
  void test() {
    RowPanelBuilder rowPanelBuilder = new RowPanelBuilder();
    assertNotNull(rowPanelBuilder.getJPanel());
    assertTrue(rowPanelBuilder.getJPanel() == rowPanelBuilder.getJPanel());
  }
}
