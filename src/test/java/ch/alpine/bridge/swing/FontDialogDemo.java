// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;

class FontDialogDemo {
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    FontDialog fontDialog = new FontDialog(null, new Font(Font.DIALOG_INPUT, Font.BOLD, 34), lt -> {
      // ---
    });
    fontDialog.setLocation(100, 200);
    fontDialog.setVisible(true);
  }
}
