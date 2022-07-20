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
      // graphics.setColor(Color.WHITE);
      // graphics.fillRect(0, 0, dimension.width, dimension.height);
      LocalTimeDisplay.INSTANCE.draw(graphics, localTimeParam.toLocalTime(), point);
    }
  };
  private final LocalTimeParam localTimeParam;

  /** @param component
   * @param localTime_fallback
   * @param consumer */
  public LocalTimeDialog(Component component, final LocalTime localTime_fallback, Consumer<LocalTime> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    setTitle("LocalTime selection");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    jComponent.setPreferredSize(new Dimension(120, 100));
    jPanel.add(jComponent, BorderLayout.WEST);
    // ---
    localTimeParam = new LocalTimeParam(localTime_fallback);
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localTimeParam);
    {
      panelFieldsEditor.addUniversalListener( //
          () -> {
            jComponent.repaint();
            consumer.accept(localTimeParam.toLocalTime());
          });
      jPanel.add(panelFieldsEditor.getJPanel(), BorderLayout.CENTER);
    }
    jPanel.add(new JLabel("\u3000"), BorderLayout.EAST);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      {
        JButton jButton = new JButton("Now");
        jButton.addActionListener(actionEvent -> {
          localTimeParam.set(LocalTime.now());
          panelFieldsEditor.updateJComponents();
          panelFieldsEditor.notifyUniversalListeners();
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Done");
        jButton.addActionListener(actionEvent -> {
          dispose();
          consumer.accept(localTimeParam.toLocalTime());
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Cancel");
        jButton.addActionListener(actionEvent -> {
          dispose();
          consumer.accept(localTime_fallback);
        });
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    setContentPane(jPanel);
    setSize(320, StaticHelper.WINDOW_MARGIN + jPanel.getPreferredSize().height);
    setResizable(false);
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
