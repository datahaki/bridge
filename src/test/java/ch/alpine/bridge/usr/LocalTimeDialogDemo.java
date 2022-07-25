// code by jph
package ch.alpine.bridge.usr;

import java.time.LocalTime;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.LocalTimeDialog;
import ch.alpine.bridge.swing.LookAndFeels;

enum LocalTimeDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    LocalTimeDialog fontDialog = new LocalTimeDialog(LocalTime.now()) {
      @Override
      public void selection(LocalTime current) {
        // ---
      }
    };
    JDialog jDialog = DialogBuilder.create(null, fontDialog);
    jDialog.setLocation(100, 200);
    jDialog.setVisible(true);
  }
}
