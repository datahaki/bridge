// code by jph
package ch.alpine.java.ref.gui;

import java.util.List;

import ch.alpine.java.ref.FieldPanel;

public interface FieldsEditor {
  List<FieldPanel> list();

  /** @param runnable that will be run if any value in editor was subject to change */
  void addUniversalListener(Runnable runnable);
}
