// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class WindowProviderTest implements WindowProvider {
  @Override
  public Window getWindow() {
    return new JFrame();
  }

  @Test
  void test() {
    Window window = new WindowProviderTest().runStandalone();
    window.setVisible(false);
  }
}
