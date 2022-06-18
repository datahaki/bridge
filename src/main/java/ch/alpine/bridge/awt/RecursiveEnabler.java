// code by gjoel
package ch.alpine.bridge.awt;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public enum RecursiveEnabler {
  ;
  /** function visits subcomponent of given components and changes enabled status to given value
   * 
   * @param jComponent
   * @param enabled */
  public static void setEnabled(JComponent jComponent, boolean enabled) {
    if (jComponent instanceof JScrollPane)
      setEnabled(((JScrollPane) jComponent).getViewport(), enabled); // ignore scrollbar
    else {
      jComponent.setEnabled(enabled);
      for (Component component : jComponent.getComponents())
        if (component instanceof JComponent)
          setEnabled((JComponent) component, enabled);
    }
  }
}
