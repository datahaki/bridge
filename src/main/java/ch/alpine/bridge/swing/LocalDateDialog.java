// code by jph
package ch.alpine.bridge.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class LocalDateDialog extends JDialog {
  /** @param component
   * @param localDate_fallback
   * @param consumer */
  public LocalDateDialog(Component component, final LocalDate localDate_fallback, Consumer<LocalDate> consumer) {
    super(JOptionPane.getFrameForComponent(component));
    setTitle("LocalDate selection");
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    // ---
    LocalDateParam localDateParam = new LocalDateParam(localDate_fallback);
    {
      PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(localDateParam);
      panelFieldsEditor.addUniversalListener(() -> consumer.accept(localDateParam.toLocalDate()));
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
          consumer.accept(localDateParam.toLocalDate());
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Cancel");
        jButton.addActionListener(actionEvent -> {
          dispose();
          consumer.accept(localDate_fallback);
        });
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    StaticHelper.configure(this, jPanel);
    addWindowListener(new WindowAdapter() {
      /** function is called when [x] is pressed by user */
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        // propagate fallback value
        consumer.accept(localDate_fallback);
      }
    });
  }
}
