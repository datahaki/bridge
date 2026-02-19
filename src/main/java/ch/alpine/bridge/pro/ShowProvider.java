// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.swing.LookAndFeels;

@FunctionalInterface
public interface ShowProvider {
  Show getShow();

  default Window run() {
    ImageIO.setUseCache(false);
    LookAndFeels.autoDetect();
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    JFrame jFrame = ShowWindow.asFrame(getShow());
    jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
    WindowBounds.persistent(jFrame, resourceLocator.properties(WindowBounds.class));
    jFrame.setVisible(true);
    return jFrame;
  }
}
