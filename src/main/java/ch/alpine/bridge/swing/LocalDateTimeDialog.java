// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalDateTimeDialog implements DialogBuilder<LocalDateTime> {
  private final LocalDateTime localDateTime_fallback;
  private final LocalDateTimeParam localDateTimeParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param component
   * @param localDate_fallback
   * @param consumer */
  public LocalDateTimeDialog(LocalDateTime localDateTime_fallback) {
    this.localDateTime_fallback = localDateTime_fallback;
    localDateTimeParam = new LocalDateTimeParam(localDateTime_fallback);
    panelFieldsEditor = new PanelFieldsEditor(localDateTimeParam);
    panelFieldsEditor.addUniversalListener(() -> selection(localDateTimeParam.toLocalDateTime()));
  }

  @Override
  public String getTitle() {
    return "LocalDateTime selection";
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
  public LocalDateTime fallback() {
    return localDateTime_fallback;
  }

  @Override
  public LocalDateTime current() {
    return localDateTimeParam.toLocalDateTime();
  }
}
