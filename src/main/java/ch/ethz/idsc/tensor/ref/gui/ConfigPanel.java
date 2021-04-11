// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.BorderLayout;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// TODO class name
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
  private final JScrollPane jScrollPane;

  private ConfigPanel(Object object) {
    fieldPanels = FieldPanels.of(object);
    toolbarsComponent = ParametersComponent.of(fieldPanels);
    jScrollPane = toolbarsComponent.createJScrollPane();
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
    JPanel jPanel;
    jPanel = new JPanel(new BorderLayout());
    jPanel.add("North", getJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    Consumer<String> consumer = s -> {
      String text = fieldPanels.objectProperties().strings().stream().collect(Collectors.joining("\n"));
      jTextArea.setText(text);
    };
    consumer.accept(null);
    fieldPanels.addUniversalListener(consumer);
    jPanel.add("Center", jTextArea);
    return jPanel;
  }
}
