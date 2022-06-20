// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.io.ResourceData;

class ImageIconsTest {
  @Test
  void test() {
    BufferedImage bufferedImage = ResourceData.bufferedImage("/ch/alpine/bridge/io/image/album_au_gray.jpg");
    Icon icon = ImageIcons.create(bufferedImage, 32);
    assertEquals(icon.getIconWidth(), 32);
    assertEquals(icon.getIconHeight(), 32);
    JLabel jLabel = new JLabel(icon);
    jLabel.setText("abc");
  }
}
