// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalDateDialog extends DialogBase<LocalDate> {
  private final LocalDateParam localDateParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param localDate fallback */
  public LocalDateDialog(LocalDate localDate) {
    super(localDate);
    localDateParam = new LocalDateParam(localDate);
    panelFieldsEditor = new PanelFieldsEditor(localDateParam);
    panelFieldsEditor.addUniversalListener(() -> selection(current()));
  }

  @Override
  public String getTitle() {
    return "LocalDate selection";
  }

  @Override
  public Optional<JComponent> getComponentWest() {
    return Optional.empty();
  }

  @Override
  public Optional<JComponent> getComponentNorth() {
    return Optional.empty();
  }

  @Override
  public PanelFieldsEditor panelFieldsEditor() {
    return panelFieldsEditor;
  }

  @Override
  public void decorate(JToolBar jToolBar) {
    // ---
  }

  @Override
  public LocalDate current() {
    return localDateParam.toLocalDate();
  }
}
