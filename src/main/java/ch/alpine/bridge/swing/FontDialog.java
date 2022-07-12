// code by jph
package ch.alpine.bridge.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class FontDialog extends JDialog {
  private static final String DEMO = "Abc123!";
  // ---
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics _g) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      Graphics2D graphics = (Graphics2D) _g;
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, dimension.width, dimension.height);
      RenderQuality.setQuality(graphics);
      graphics.setFont(font);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int ascent = fontMetrics.getAscent();
      int stringWidth = fontMetrics.stringWidth(DEMO);
      graphics.setColor(Color.DARK_GRAY);
      graphics.drawString(DEMO, point.x - stringWidth / 2, point.y + ascent / 2);
    }
  };
  private Font font;

  public FontDialog(Component component, final Font font_fallback, Consumer<Font> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    setTitle("Font selection");
    setSize(300, 220);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    // ---
    font = font_fallback;
    jComponent.setPreferredSize(new Dimension(200, 60));
    jPanel.add(BorderLayout.NORTH, jComponent);
    // ---
    FontParam fontParam = new FontParam(font);
    {
      PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(fontParam);
      panelFieldsEditor.addUniversalListener( //
          () -> {
            font = fontParam.toFont();
            jComponent.repaint();
            consumer.accept(font);
          });
      jPanel.add(BorderLayout.CENTER, panelFieldsEditor.getJPanel());
    }
    jPanel.add(BorderLayout.WEST, new JLabel("\u3000"));
    jPanel.add(BorderLayout.EAST, new JLabel("\u3000"));
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      {
        JButton jButton = new JButton("Done");
        jButton.addActionListener(actionEvent -> {
          dispose();
          consumer.accept(font);
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Cancel");
        jButton.addActionListener(actionEvent -> {
          consumer.accept(font_fallback);
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
        consumer.accept(font_fallback);
      }
    });
  }
}
