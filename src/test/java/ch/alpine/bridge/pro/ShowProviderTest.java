// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.fig.Show;

class ShowProviderTest implements ShowProvider {
  @Override
  public Show getShow() {
    return new Show();
  }

  @Test
  void test() {
    Window window = new ShowProviderTest().runStandalone();
    window.setVisible(false);
  }
}
