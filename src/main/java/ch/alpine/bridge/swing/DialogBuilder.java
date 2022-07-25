// code by jph
package ch.alpine.bridge.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public interface DialogBuilder<T> {
  static <T> JDialog create(Component component, DialogBuilder<T> dialogBuilder) {
    JDialog jDialog = new JDialog(JOptionPane.getFrameForComponent(component));
    jDialog.setTitle(dialogBuilder.getTitle());
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(dialogBuilder.getComponentWest().orElse(new JLabel("\u3000")), BorderLayout.WEST);
    {
      Optional<JComponent> optional = dialogBuilder.getComponentNorth();
      if (optional.isPresent())
        jPanel.add(optional.orElseThrow(), BorderLayout.NORTH);
    }
    jPanel.add(dialogBuilder.panelFieldsEditor().getJPanel(), BorderLayout.CENTER);
    jPanel.add(new JLabel("\u3000"), BorderLayout.EAST);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      dialogBuilder.decorate(jToolBar);
      {
        JButton jButton = new JButton("Done");
        jButton.addActionListener(actionEvent -> {
          jDialog.dispose();
          dialogBuilder.selection(dialogBuilder.current());
        });
        jToolBar.add(jButton);
      }
      jToolBar.addSeparator();
      {
        JButton jButton = new JButton("Cancel");
        jButton.addActionListener(actionEvent -> {
          jDialog.dispose();
          dialogBuilder.selection(dialogBuilder.fallback());
        });
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    StaticHelper.configure(jDialog, jPanel);
    jDialog.addWindowListener(new WindowAdapter() {
      /** function is called when [x] is pressed by user */
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        // propagate fallback value
        dialogBuilder.selection(dialogBuilder.fallback());
      }
    });
    return jDialog;
  }

  String getTitle();

  Optional<JComponent> getComponentWest();

  PanelFieldsEditor panelFieldsEditor();

  void decorate(JToolBar jToolBar);

  T fallback();

  T current();

  void selection(T current);

  Optional<JComponent> getComponentNorth();
}
