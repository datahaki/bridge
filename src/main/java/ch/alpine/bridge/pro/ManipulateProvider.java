// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.awt.Window;

import ch.alpine.bridge.fig.Manipulate;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.LookAndFeels;

/** implementing classes are subject to introspection
 * 
 * @see ReflectionMarker */
@FunctionalInterface
public interface ManipulateProvider {
  Container getContainer();

  default Window run() {
    LookAndFeels.autoDetect();
    return Manipulate.asFrame(this, () -> getContainer());
  }
}
