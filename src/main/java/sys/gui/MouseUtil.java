// code by jph
package sys.gui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public enum MouseUtil {
  ;
  public static boolean withCtrl(MouseEvent mouseEvent) {
    return (mouseEvent.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK;
  }
}
