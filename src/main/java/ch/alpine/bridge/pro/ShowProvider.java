// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;

@FunctionalInterface
public interface ShowProvider {
  Show getShow();

  default Window run() {
    return ShowDialog.of(getShow());
  }
}
