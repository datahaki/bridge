// code by jph
package ch.alpine.bridge.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class LocalDateTimeDialog extends JDialog {
  private LocalDateTime localDateTime;

  /** @param component
   * @param localDate_fallback
   * @param consumer */
  public LocalDateTimeDialog(Component component, final LocalDateTime localDateTime_fallback, Consumer<LocalDateTime> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    setTitle("LocalDateTime selection");
    setSize(220, 250);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
   
    // ---
    localDateTime =localDateTime_fallback;
    // ---
    LocalDateTimeParam localDateTimeParam = new LocalDateTimeParam(localDateTime);
    {
      PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localDateTimeParam);
      panelFieldsEditor.addUniversalListener( //
          () -> {
            localDateTime = localDateTimeParam.toLocalDateTime();
            consumer.accept(localDateTime);
          });
      jPanel.add(panelFieldsEditor.getJPanel(), BorderLayout.CENTER);
    }
    jPanel.add(new JLabel("\u3000"), BorderLayout.EAST);
    jPanel.add(new JLabel("\u3000"), BorderLayout.WEST);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      {
        JButton jButton = new JButton("Done");
        jButton.addActionListener(actionEvent -> {
          dispose();
          consumer.accept(localDateTime);
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Cancel");
        jButton.addActionListener(actionEvent -> {
          consumer.accept(localDateTime_fallback);
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
        consumer.accept(localDateTime_fallback);
      }
    });
  }
}
