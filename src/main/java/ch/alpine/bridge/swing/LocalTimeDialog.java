// code by jph
package ch.alpine.bridge.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.time.LocalTime;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.gfx.LocalTimeDisplay;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalTimeDialog implements DialogBuilder<LocalTime> {
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics _g) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      Graphics2D graphics = (Graphics2D) _g;
      LocalTimeDisplay.INSTANCE.draw(graphics, localTimeParam.toLocalTime(), point);
    }
  };
  private final LocalTime localTime_fallback;
  private final LocalTimeParam localTimeParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param component
   * @param localTime_fallback
   * @param consumer */
  public LocalTimeDialog(final LocalTime localTime_fallback) {
    this.localTime_fallback = localTime_fallback;
    localTimeParam = new LocalTimeParam(localTime_fallback);
    // ---
    jComponent.setPreferredSize(new Dimension(120, 100));
    // ---
    panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
    {
      panelFieldsEditor.addUniversalListener( //
          () -> {
            jComponent.repaint();
            selection(localTimeParam.toLocalTime());
          });
    }
  }

  @Override
  public String getTitle() {
    return "LocalTime selection";
  }

  @Override
  public Optional<JComponent> getComponentWest() {
    return Optional.of(jComponent);
  }

  @Override
  public PanelFieldsEditor panelFieldsEditor() {
    return panelFieldsEditor;
  }

  @Override
  public void decorate(JToolBar jToolBar) {
    JButton jButton = new JButton("Now");
    jButton.addActionListener(actionEvent -> {
      localTimeParam.set(LocalTime.now());
      panelFieldsEditor.updateJComponents();
      panelFieldsEditor.notifyUniversalListeners();
    });
    jToolBar.add(jButton);
    jToolBar.addSeparator();
  }

  @Override
  public LocalTime fallback() {
    return localTime_fallback;
  }

  @Override
  public LocalTime current() {
    return localTimeParam.toLocalTime();
  }

  @Override
  public Optional<JComponent> getComponentNorth() {
    return Optional.empty();
  }
}
