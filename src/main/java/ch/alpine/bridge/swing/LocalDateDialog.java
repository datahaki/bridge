// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalDateDialog implements DialogBuilder<LocalDate> {
  private final LocalDate localDate_fallback;
  private final LocalDateParam localDateParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param component
   * @param localDate_fallback
   * @param consumer */
  public LocalDateDialog(final LocalDate localDate_fallback) {
    this.localDate_fallback = localDate_fallback;
    localDateParam = new LocalDateParam(localDate_fallback);
    panelFieldsEditor = new PanelFieldsEditor(localDateParam);
    panelFieldsEditor.addUniversalListener(() -> selection(localDateParam.toLocalDate()));
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
  public PanelFieldsEditor panelFieldsEditor() {
    return panelFieldsEditor;
  }

  @Override
  public void decorate(JToolBar jToolBar) {
    // ---
  }

  @Override
  public LocalDate fallback() {
    return localDate_fallback;
  }

  @Override
  public LocalDate current() {
    return localDateParam.toLocalDate();
  }

  @Override
  public Optional<JComponent> getComponentNorth() {
    return Optional.empty();
  }
}
