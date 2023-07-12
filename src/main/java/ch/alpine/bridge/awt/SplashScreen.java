// code by jph
package ch.alpine.bridge.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import ch.alpine.tensor.ext.ResourceData;

/** SplashScreen is an alternative to {@link java.awt.SplashScreen} */
public enum SplashScreen {
  ;
  public static JDialog create(String string) {
    return create(ResourceData.bufferedImage(string));
  }

  public static JDialog create(BufferedImage bufferedImage) {
    JDialog jDialog = new JDialog();
    jDialog.setUndecorated(true);
    jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    Dimension dimension = new Dimension( //
        bufferedImage.getWidth(), //
        bufferedImage.getHeight());
    JComponent jComponent = new JComponent() {
      @Override
      protected void paintComponent(Graphics graphics) {
        graphics.drawImage(bufferedImage, 0, 0, this);
      }
    };
    jComponent.setPreferredSize(dimension);
    jDialog.setContentPane(jComponent);
    jComponent.addMouseListener(new LazyMouse(mouseEvent -> jDialog.dispose()));
    jDialog.setSize(dimension);
    jDialog.setAlwaysOnTop(true);
    jDialog.setLocationRelativeTo(null);
    jDialog.setVisible(true);
    return jDialog;
  }
}
