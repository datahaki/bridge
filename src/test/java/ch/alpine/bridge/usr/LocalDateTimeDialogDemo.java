// code by omk
package ch.alpine.bridge.usr;

import java.time.LocalDateTime;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.LocalDateTimeDialog;
import ch.alpine.bridge.swing.LookAndFeels;

enum LocalDateTimeDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    LocalDateTimeDialog localDateTimeDialog = new LocalDateTimeDialog(LocalDateTime.now()) {
      @Override
      public void selection(LocalDateTime current) {
        // ---
      }
    };
    JDialog jDialog = DialogBuilder.create(null, localDateTimeDialog);
    jDialog.setLocation(100, 200);
    jDialog.setVisible(true);
  }
}
