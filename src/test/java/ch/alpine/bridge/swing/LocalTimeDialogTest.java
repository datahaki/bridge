// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class LocalTimeDialogTest {
  @Test
  void test() throws InterruptedException {
    LocalTimeDialog localTimeDialog = new LocalTimeDialog(null, LocalTime.now(), lt -> {
      // ---
    });
    localTimeDialog.setLocation(100, 200);
    localTimeDialog.setVisible(true);
    Thread.sleep(100);
    localTimeDialog.dispose();
  }
}
