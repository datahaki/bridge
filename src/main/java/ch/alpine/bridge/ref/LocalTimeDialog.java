// code by jph
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ch.alpine.bridge.gfx.LocalTimeDisplay;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class LocalTimeDialog extends JDialog {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics _g) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      Graphics2D graphics = (Graphics2D) _g;
      LocalTimeDisplay.INSTANCE.drawClock(graphics, localTime, point);
    }
  };
  private LocalTime localTime;

  /** @param component
   * @param _localTime
   * @param consumer */
  public LocalTimeDialog(Component component, final LocalTime _localTime, Consumer<LocalTime> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    this.localTime = _localTime;
    setTitle("LocalTime selection");
    LocalTimeParam localTimeParam = new LocalTimeParam(localTime);
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
    jComponent.setPreferredSize(new Dimension(130, 130));
    jPanel.add(BorderLayout.WEST, jComponent);
    jPanel.add(BorderLayout.EAST, new JLabel("\u3000"));
    jPanel.add(BorderLayout.CENTER, panelFieldsEditor.getJPanel());
    JToolBar jToolBar = new JToolBar();
    jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
    {
      JButton jButton = new JButton("Done");
      jButton.addActionListener(e -> dispose());
      jToolBar.add(jButton);
    }
    {
      JButton jButton = new JButton("Cancel");
      jButton.addActionListener(e -> {
        consumer.accept(_localTime);
        dispose();
      });
      jToolBar.add(jButton);
    }
    jPanel.add(BorderLayout.SOUTH, jToolBar);
    setContentPane(jPanel);
    panelFieldsEditor.addUniversalListener( //
        () -> {
          localTime = localTimeParam.toLocalTime();
          consumer.accept(localTime);
          jComponent.repaint();
        });
    setSize(320, 200);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        consumer.accept(_localTime);
      }
    });
  }
}
