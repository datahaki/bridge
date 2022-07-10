// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalTime;

enum LocalTimeDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    LocalTimeDialog fontDialog = new LocalTimeDialog(null, LocalTime.now(), lt -> {
      // ---
    });
    fontDialog.setLocation(100, 200);
    fontDialog.setVisible(true);
  }
}
