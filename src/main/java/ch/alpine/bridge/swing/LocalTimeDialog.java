// code by jph
package ch.alpine.bridge.swing;

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
import javax.swing.WindowConstants;

import ch.alpine.bridge.gfx.LocalTimeDisplay;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class LocalTimeDialog extends JDialog {
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics _g) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      Graphics2D graphics = (Graphics2D) _g;
      LocalTimeDisplay.INSTANCE.draw(graphics, localTime, point);
    }
  };
  private LocalTime localTime;

  /** @param component
   * @param localTime_fallback
   * @param consumer */
  public LocalTimeDialog(Component component, final LocalTime localTime_fallback, Consumer<LocalTime> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    setTitle("LocalTime selection");
    setSize(320, 200);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    // ---
    localTime = localTime_fallback;
    jComponent.setPreferredSize(new Dimension(130, 130));
    jPanel.add(BorderLayout.WEST, jComponent);
    // ---
    LocalTimeParam localTimeParam = new LocalTimeParam(localTime);
    {
      PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
      panelFieldsEditor.addUniversalListener( //
          () -> {
            localTime = localTimeParam.toLocalTime();
            consumer.accept(localTime);
            jComponent.repaint();
          });
      jPanel.add(BorderLayout.CENTER, panelFieldsEditor.getJPanel());
    }
    jPanel.add(BorderLayout.EAST, new JLabel("\u3000"));
    {
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
          consumer.accept(localTime_fallback);
          dispose();
        });
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    setContentPane(jPanel);
    addWindowListener(new WindowAdapter() {
      /** function is called when [x] is pressed by user */
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        // propagate fallback value
        consumer.accept(localTime_fallback);
      }
    });
  }
}
