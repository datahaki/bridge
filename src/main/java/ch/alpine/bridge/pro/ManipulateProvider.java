// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.swing.JComponent;

import ch.alpine.bridge.fig.Manipulate;
import ch.alpine.bridge.swing.LookAndFeels;

public interface ManipulateProvider {
  JComponent getJComponent();

  default Window run() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    return Manipulate.asFrame(this, () -> getJComponent());
  }
}
