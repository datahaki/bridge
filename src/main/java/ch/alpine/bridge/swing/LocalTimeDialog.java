// code by jph
package ch.alpine.bridge.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.time.LocalTime;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.gfx.LocalTimeDisplay;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class LocalTimeDialog extends DialogBase<LocalTime> {
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      LocalTimeDisplay.draw(graphics, current(), point);
    }
  };
  private final LocalTimeParam localTimeParam;
  private final PanelFieldsEditor panelFieldsEditor;

  /** @param localTime fallback */
  protected LocalTimeDialog(LocalTime localTime) {
    super(localTime);
    localTimeParam = new LocalTimeParam(localTime);
    // ---
    jComponent.setPreferredSize(new Dimension(120, 100));
    // ---
    panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
    panelFieldsEditor.addUniversalListener(() -> {
      jComponent.repaint();
      selection(current());
    });
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
      localTimeParam.set(LocalTime.now());
      panelFieldsEditor.updateJComponents();
      panelFieldsEditor.notifyUniversalListeners();
    });
    jToolBar.add(jButton);
    jToolBar.addSeparator();
  }

  @Override
  public LocalTime current() {
    return localTimeParam.toLocalTime();
  }
}
