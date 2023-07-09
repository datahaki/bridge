// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class Col2PanelBuilderTest {
  @Test
  void test() {
    Col2PanelBuilder rowPanelBuilder = new Col2PanelBuilder();
    assertNotNull(rowPanelBuilder.getJComponent());
    assertSame(rowPanelBuilder.getJComponent(), rowPanelBuilder.getJComponent());
  }
}
