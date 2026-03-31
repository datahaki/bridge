// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.swing.LookAndFeels;

public non-sealed interface WindowProvider extends RunProvider {
  /** Careful: multiple invocations may return the same instance, or a new window
   * 
   * @return */
  Window getWindow();

  @Override
  default Window runStandalone() {
    ImageIO.setUseCache(false);
    LookAndFeels.autoDetect();
    Window window = getWindow();
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    WindowBounds.persistent(window, resourceLocator.properties(WindowBounds.class));
    if (window instanceof JFrame jFrame) {
      jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      if (jFrame.getTitle().isEmpty())
        jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
      AwtUtil.ctrlW(jFrame);
    }
    if (window instanceof JDialog jDialog) {
      jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      if (jDialog.getTitle().isEmpty())
        jDialog.setTitle(FriendlyFormat.defaultTitle(getClass()));
      AwtUtil.ctrlW(jDialog);
    }
    window.setVisible(true);
    return window;
  }
}
