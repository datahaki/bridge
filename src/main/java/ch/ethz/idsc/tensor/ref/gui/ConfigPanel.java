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

public class ConfigPanel {
  /** @param object
   * @return */
  public static ConfigPanel of(Object object) {
    return new ConfigPanel(object);
  }

  /***************************************************/
  private final FieldPanels fieldPanels;
  private final ToolbarsComponent toolbarsComponent;
  private final JPanel jPanel;

  private ConfigPanel(Object object) {
    fieldPanels = FieldPanels.of(object);
    toolbarsComponent = ParametersComponent.of(fieldPanels);
    jPanel = new JPanel(new BorderLayout());
    jPanel.add("North", toolbarsComponent.getScrollPane());
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

  public ToolbarsComponent toolbarsComponent() {
    return toolbarsComponent;
  }

  public FieldPanels fieldPanels() {
    return fieldPanels;
  }

  /** @return component that contains the labeled input fields only, but not
   * the text summary below */
  public JScrollPane getFields() {
    return toolbarsComponent.getScrollPane();
  }

  /** @return */
  public JComponent getFieldsAndTextarea() {
    return jPanel;
  }
}
