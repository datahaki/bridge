

package ch.alpine.bridge.usr;

import java.time.LocalDateTime;
import ch.alpine.bridge.swing.LocalDateTimeDialog;
import ch.alpine.bridge.swing.LookAndFeels;

enum LocalDateTimeDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    LocalDateTimeDialog fontDialog = new LocalDateTimeDialog(null, LocalDateTime.now(), lt -> {
      // ---
    });
    fontDialog.setLocation(100, 200);
    fontDialog.setVisible(true);
  }
}
