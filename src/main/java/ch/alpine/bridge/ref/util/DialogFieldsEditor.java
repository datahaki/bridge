// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

/** the standalone dialog allows the user to modify the contents of the given object
 * (changes take effect immediately) and finally decide whether to affirm or cancel. */
public class DialogFieldsEditor extends JDialog {
  private static final int MARGIN_WIDTH = 200;
  private static final int MARGIN_HEIGHT = 60;

  /** @param parentComponent may be null
   * @param title of dialog window
   * @param object non-null
   * @return dialog */
  public static DialogFieldsEditor show(Component parentComponent, String title, Object object) {
    DialogFieldsEditor dialogFieldsEditor = new DialogFieldsEditor(parentComponent, title, object);
    dialogFieldsEditor.setLocationRelativeTo(parentComponent);
    dialogFieldsEditor.setVisible(true);
    return dialogFieldsEditor;
  }

  // ---
  private final Object object;
  private final PanelFieldsEditor panelFieldsEditor;
  private boolean status = false;
  private String fallback;

  /** @param parentComponent may be null
   * @param title of dialog window
   * @param object non-null
   * @return dialog */
  public DialogFieldsEditor(Component parentComponent, String title, Object object) {
    super(JOptionPane.getFrameForComponent(parentComponent));
    setTitle(title);
    // , title, Dialog.DEFAULT_MODALITY_TYPE
    this.object = Objects.requireNonNull(object);
    panelFieldsEditor = new PanelFieldsEditor(object);
    fallback = ObjectProperties.join(object);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        cancel();
      }
    });
    // ---
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(panelFieldsEditor.createJScrollPane(), BorderLayout.CENTER);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      {
        JButton jButton = new JButton("Done");
        jButton.setToolTipText("close dialog and keep current values");
        jButton.addActionListener(actionEvent -> {
          status = true;
          dispose();
        });
        jToolBar.add(jButton);
      }
      {
        JButton jButton = new JButton("Revert");
        jButton.setToolTipText("revert to initial values");
        jButton.addActionListener(actionEvent -> {
          ObjectProperties.part(object, fallback);
          panelFieldsEditor.updateJComponents();
        });
        jToolBar.add(jButton);
      }
      {
        JButton jButton = new JButton("Cancel");
        jButton.setToolTipText("close dialog and revert to initial values");
        jButton.addActionListener(actionEvent -> cancel());
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    setContentPane(jPanel);
    Dimension dimension = getPreferredSize();
    setSize(new Dimension(dimension.width + MARGIN_WIDTH, dimension.height + MARGIN_HEIGHT));
  }

  public FieldsEditor fieldsEditor() {
    return panelFieldsEditor;
  }

  /** @return optional that is non-empty with given object only if user has pressed button "Ok" */
  public Optional<Object> getSelection() {
    return status //
        ? Optional.of(object)
        : Optional.empty();
  }

  private void cancel() {
    ObjectProperties.part(object, fallback);
    dispose();
  }
}