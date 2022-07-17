// code by jph
package ch.alpine.bridge.usr;

import java.time.LocalTime;

import ch.alpine.bridge.swing.LocalTimeDialog;
import ch.alpine.bridge.swing.LookAndFeels;

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
