// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.BorderLayout;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.ethz.idsc.tensor.ref.ObjectProperties;

// TODO class name
// TODO method names
public class ConfigPanel {
  /** @param object non-null
   * @return
   * @throws Exception if given object is null */
  public static ConfigPanel of(Object object) {
    return new ConfigPanel(object);
  }

  /***************************************************/
  private final FieldPanels fieldPanels;
  private final ToolbarsComponent toolbarsComponent;
  private final JPanel jPanel;
  private final JScrollPane jScrollPane;

  private ConfigPanel(Object object) {
    fieldPanels = FieldPanels.of(object);
    toolbarsComponent = ParametersComponent.of(fieldPanels);
    jPanel = new JPanel(new BorderLayout());
    jScrollPane = toolbarsComponent.createJScrollPane();
    jPanel.add("North", jScrollPane);
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    Consumer<String> consumer = s -> {
      String text = ObjectProperties.wrap(object).strings().stream().collect(Collectors.joining("\n"));
      jTextArea.setText(text);
    };
    consumer.accept(null);
    fieldPanels.addUniversalListener(consumer);
    jPanel.add("Center", jTextArea);
  }

  public ToolbarsComponent getToolbarsComponent() {
    return toolbarsComponent;
  }

  public FieldPanels getFieldPanels() {
    return fieldPanels;
  }

  public JScrollPane getJScrollPane() {
    return jScrollPane;
  }

  /** @return */
  public JComponent getFieldsAndTextarea() {
    return jPanel;
  }
}
