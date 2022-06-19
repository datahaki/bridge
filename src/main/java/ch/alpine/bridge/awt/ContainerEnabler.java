// code by gjoel
package ch.alpine.bridge.awt;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public enum ContainerEnabler {
  ;
  /** function visits subcomponent of given container and changes enabled status
   * of components in the subcomponent tree to given value
   * 
   * @param container
   * @param enabled */
  public static void setEnabled(Container container, boolean enabled) {
    if (container instanceof JScrollPane)
      setEnabled(((JScrollPane) container).getViewport(), enabled); // ignore scrollbar
    else {
      container.setEnabled(enabled);
      for (Component component : container.getComponents())
        if (component instanceof JComponent)
          setEnabled((JComponent) component, enabled);
    }
  }
}
