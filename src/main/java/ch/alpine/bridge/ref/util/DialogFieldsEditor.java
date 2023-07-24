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
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.ref.FieldsEditorParam;

/** the standalone dialog allows the user to modify the contents of the given object
 * (changes take effect immediately) and finally decide whether to affirm or cancel. */
public class DialogFieldsEditor extends JDialog {
  private static final int MARGIN_WIDTH = 200;
  private static final int MARGIN_HEIGHT = 60;

  /** @param parentComponent may be null
   * @param object non-null
   * @param title of dialog window, may be null
   * @return */
  public static DialogFieldsEditor show(Component parentComponent, Object object, String title) {
    return show(parentComponent, object, title, null);
  }

  /** @param parentComponent
   * @param object non-null
   * @param title
   * @param consumer
   * @return */
  public static DialogFieldsEditor show(Component parentComponent, Object object, String title, Consumer<Object> consumer) {
    DialogFieldsEditor dialogFieldsEditor = new DialogFieldsEditor(parentComponent, title, false, object);
    dialogFieldsEditor.setLocationRelativeTo(parentComponent);
    if (Objects.nonNull(consumer))
      WindowClosed.runs(dialogFieldsEditor, () -> dialogFieldsEditor.getSelection().ifPresent(consumer));
    ScreenRectangles.create().placement(dialogFieldsEditor);
    dialogFieldsEditor.setVisible(true);
    return dialogFieldsEditor;
  }

  /** @param parentComponent
   * @param object
   * @param title
   * @return */
  @SuppressWarnings("unchecked")
  public static <T> Optional<T> block(Component parentComponent, T object, String title) {
    DialogFieldsEditor dialogFieldsEditor = new DialogFieldsEditor(parentComponent, title, true, object);
    dialogFieldsEditor.setLocationRelativeTo(parentComponent);
    ScreenRectangles.create().placement(dialogFieldsEditor);
    dialogFieldsEditor.setVisible(true);
    return dialogFieldsEditor.getSelection().map(obj -> (T) obj);
  }

  // ---
  private final Object object;
  private final PanelFieldsEditor panelFieldsEditor;
  private final String fallback;
  private boolean status = false;

  /** @param parentComponent may be null
   * @param title of dialog window, may be null
   * @param modal
   * @param object non-null
   * @return dialog */
  private DialogFieldsEditor(Component parentComponent, String title, boolean modal, Object object) {
    super(JOptionPane.getFrameForComponent(parentComponent), title, modal);
    this.object = Objects.requireNonNull(object);
    panelFieldsEditor = PanelFieldsEditor.splits(object);
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
        FieldsEditorParam.GLOBAL.minSize(jButton);
        jButton.setToolTipText("close dialog and keep current values");
        jButton.addActionListener(actionEvent -> {
          status = true;
          dispose();
        });
        jToolBar.add(jButton);
      }
      {
        JButton jButton = new JButton("Revert");
        FieldsEditorParam.GLOBAL.minSize(jButton);
        jButton.setToolTipText("revert to initial values");
        jButton.addActionListener(actionEvent -> {
          ObjectProperties.part(object, fallback);
          panelFieldsEditor.updateJComponents();
        });
        jToolBar.add(jButton);
      }
      {
        JButton jButton = new JButton("Cancel");
        FieldsEditorParam.GLOBAL.minSize(jButton);
        jButton.setToolTipText("close dialog and revert to initial values");
        jButton.addActionListener(actionEvent -> cancel());
        jToolBar.add(jButton);
      }
      jPanel.add(BorderLayout.SOUTH, jToolBar);
    }
    // jPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
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
