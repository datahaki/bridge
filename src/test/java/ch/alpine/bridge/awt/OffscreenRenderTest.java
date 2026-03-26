// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

class OffscreenRenderTest {
  @Test
  void test() {
    JLabel jLabel = new JLabel();
    assertThrows(Exception.class, () -> OffscreenRender.of(jLabel));
  }
}
