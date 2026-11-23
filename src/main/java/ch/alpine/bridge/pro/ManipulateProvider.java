// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.io.ProcessLeaseGuard;
import ch.alpine.bridge.io.ProcessLeaseGuard.Lease;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.LookAndFeels;

/** implementing classes are subject to introspection
 * 
 * @see ReflectionMarker */
public non-sealed interface ManipulateProvider extends RunProvider {
  /** @return not null */
  Container getContainer();

  /** non-blocking
   * 
   * @return
   * @apiNote should not be used for testing */
  @Override
  default JFrame runStandalone() {
    ImageIO.setUseCache(false);
    LookAndFeels.autoDetect();
    ProcessLeaseGuard guard = new ProcessLeaseGuard(getClass().getName());
    Optional<Lease> optional = guard.tryAcquire();
    if (optional.isPresent()) {
      Lease lease = optional.orElseThrow();
      ResourceLocator resourceLocator = ResourceLocator.of(getClass());
      resourceLocator.tryLoad(this); // assign field values from properties file
      JFrame jFrame = Manipulate.asFrame(this, this::getContainer);
      jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
      WindowBounds.persistent(jFrame, resourceLocator.properties(WindowBounds.class));
      WindowClosed.runs(jFrame, () -> resourceLocator.trySave(this)); // store field values
      WindowClosed.runs(jFrame, this::windowClosed); // store field values
      WindowClosed.runs(jFrame, lease::close);
      AwtUtil.ctrlW(jFrame);
      SwingUtilities.invokeLater(() -> jFrame.setVisible(true));
      return jFrame;
    }
    System.err.println("already running");
    return null;
  }

  /** invoked when window closes, allows app to perform clean up
   * e.g. thread stop */
  default void windowClosed() {
  }
}
