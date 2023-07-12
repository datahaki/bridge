// code by jph
package ch.alpine.bridge.awt;

import java.awt.image.BufferedImage;

import javax.swing.JDialog;

import org.junit.jupiter.api.Test;

class SplashScreenTest {
  @Test
  void test() throws InterruptedException {
    JDialog jDialog = SplashScreen.create(new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB));
    Thread.sleep(200);
    jDialog.dispose();
  }
}
