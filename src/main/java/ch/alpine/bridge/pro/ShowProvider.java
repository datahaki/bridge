// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowWindow;
import ch.alpine.bridge.swing.LookAndFeels;

@FunctionalInterface
public interface ShowProvider {
  Show getShow();

  default Window run() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    return ShowWindow.asFrame(getShow());
  }
}
