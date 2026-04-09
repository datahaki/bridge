// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.LookAndFeels;

/** implementing classes are subject to introspection
 * 
 * @see ReflectionMarker */
public non-sealed interface ManipulateProvider extends RunProvider {
  /** @return not null */
  Container getContainer();

  /** @return
   * @apiNote should not be used for testing */
  @Override
  default JFrame runStandalone() {
    ImageIO.setUseCache(false);
    LookAndFeels.autoDetect();
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    resourceLocator.tryLoad(this); // assign field values from properties file
    JFrame jFrame = Manipulate.asFrame(this, this::getContainer);
    jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
    WindowBounds.persistent(jFrame, resourceLocator.properties(WindowBounds.class));
    WindowClosed.runs(jFrame, () -> resourceLocator.trySave(this)); // store field values
    AwtUtil.ctrlW(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }
}
