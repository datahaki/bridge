// code by jph
package ch.alpine.bridge.usr;

import java.awt.Font;

import javax.swing.JDialog;

import ch.alpine.bridge.swing.DialogBuilder;
import ch.alpine.bridge.swing.FontDialog;
import ch.alpine.bridge.swing.LookAndFeels;

enum FontDialogDemo {
  ;
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    FontDialog fontDialog = new FontDialog(new Font(Font.DIALOG_INPUT, Font.BOLD, 34)) {
      @Override
      public void selection(Font current) {
        // ---
      }
    };
    JDialog jDialog = DialogBuilder.create(null, fontDialog);
    jDialog.setLocation(100, 200);
    jDialog.setVisible(true);
  }
}
