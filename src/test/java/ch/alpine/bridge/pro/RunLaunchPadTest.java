// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import org.junit.jupiter.api.Test;

class RunLaunchPadTest {
  @Test
  void test() throws Exception {
    Window window = RunLaunchPad.create(RunLaunchPadTest.class.getPackageName()).runStandalone();
    window.setVisible(false);
  }
}
