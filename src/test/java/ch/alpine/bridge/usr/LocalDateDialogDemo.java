// code by jph
package ch.alpine.bridge.usr;

import java.time.LocalDate;

import ch.alpine.bridge.swing.LocalDateDialog;
import ch.alpine.bridge.swing.LookAndFeels;

enum LocalDateDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    LocalDateDialog fontDialog = new LocalDateDialog(null, LocalDate.now(), lt -> {
      // ---
    });
    fontDialog.setLocation(100, 200);
    fontDialog.setVisible(true);
  }
}
