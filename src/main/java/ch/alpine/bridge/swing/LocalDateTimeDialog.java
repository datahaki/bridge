// code by jph
package ch.alpine.bridge.swing;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalDateTimeDialog extends DialogBase<LocalDateTime> {
  private final LocalDateTimeParam localDateTimeParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param localDateTime fallback */
  protected LocalDateTimeDialog(LocalDateTime localDateTime) {
    super(localDateTime);
    localDateTimeParam = new LocalDateTimeParam(localDateTime);
    panelFieldsEditor = new PanelFieldsEditor(localDateTimeParam);
    panelFieldsEditor.addUniversalListener(() -> selection(current()));
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
    JButton jButton = new JButton("Now");
    jButton.addActionListener(actionEvent -> {
      localDateTimeParam.set(LocalDateTime.now());
      panelFieldsEditor.updateJComponents();
      panelFieldsEditor.notifyUniversalListeners();
    });
    jToolBar.add(jButton);
    jToolBar.addSeparator();
  }

  @Override
  public LocalDateTime current() {
    return localDateTimeParam.toLocalDateTime();
  }
}
