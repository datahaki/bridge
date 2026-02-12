// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.swing.JComponent;

import ch.alpine.bridge.fig.Manipulate;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.LookAndFeels;

/** implementing classes are subject to introspection
 * 
 * @see ReflectionMarker */
public interface ManipulateProvider {
  JComponent getJComponent();

  default Window run() {
    LookAndFeels.autoDetect();
    return Manipulate.asFrame(this, () -> getJComponent());
  }
}
