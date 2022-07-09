// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;

import org.junit.jupiter.api.Test;

class FontDialogTest {
  @Test
  void test() {
    FontDialog fontDialog = new FontDialog(null, new Font(Font.DIALOG, Font.BOLD, 4), e -> {
      // ---
    });
    fontDialog.getTitle();
  }
}
