// code by gjoel
package ch.alpine.bridge.swing;

import java.awt.Window;
import java.util.Objects;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

/** Do not invoke LookAndFeel#getDefaults()
 * but modify keys only via UIManager.getDefaults().
 * 
 * as of April 2022, this function should be called
 * once before any GUI is initialized.
 * 
 * changing the layout while GUI elements are already visible
 * will result in exceptions thrown, and possibly the
 * program not terminating at all! */
public enum LookAndFeels {
  /** java swing default */
  DEFAULT(new MetalLookAndFeel()), //
  DARK(new FlatDarkLaf()), //
  LIGHT(new FlatLightLaf()), //
  // does not allow LookAndFeel#getDefaults()
  CDE_MOTIF("com.sun.java.swing.plaf.motif.MotifLookAndFeel"), //
  DRACULA(new FlatDarculaLaf()), //
  INTELLI_J(new FlatIntelliJLaf()), //
  /** synth gives trouble on linux: dash pc, jan's pc ... */
  SYNTH(new SynthLookAndFeel()), //
  NIMBUS(new NimbusLookAndFeel()), //
  GTK_PLUS("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

  private final LookAndFeel lookAndFeel;
  private final String className;

  private LookAndFeels(LookAndFeel lookAndFeel) {
    this.lookAndFeel = lookAndFeel;
    className = lookAndFeel.getClass().getName();
  }

  LookAndFeels(String className) {
    lookAndFeel = null;
    this.className = className;
  }

  public LookAndFeel get() {
    return lookAndFeel;
  }

  public boolean tryUpdateUI() {
    try {
      updateUI();
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return false;
  }

  public void updateUI() throws Exception {
    if (Objects.nonNull(lookAndFeel))
      UIManager.setLookAndFeel(lookAndFeel);
    else
      UIManager.setLookAndFeel(className);
    for (Window window : Window.getWindows())
      SwingUtilities.updateComponentTreeUI(window);
  }
}