// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalTime;

import javax.swing.JDialog;

import org.junit.jupiter.api.Test;

class LocalTimeDialogTest {
  @Test
  void test() throws InterruptedException {
    LocalTimeDialog localTimeDialog = new LocalTimeDialog(LocalTime.now()) {
      @Override
      public void selection(LocalTime current) {
        // ---
      }
    };
    JDialog jDialog = DialogBuilder.create(null, localTimeDialog);
    jDialog.setLocation(100, 200);
    jDialog.setVisible(true);
    Thread.sleep(100);
    jDialog.dispose();
  }
}
