// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldLabel;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;

/** component that generically inspects a given object for fields of type
 * {@link Tensor} and {@link Scalar}. For each such field, a text field
 * is provided that allows the modification of the value. */
// TODO class name
public class ConfigPanel {
  /** @param object non-null
   * @return
   * @throws Exception if given object is null */
  public static ConfigPanel of(Object object) {
    return new ConfigPanel(object);
  }

  /** @param fieldPanels
   * @return */
  private static ToolbarsComponent build(FieldPanels fieldPanels) {
    ToolbarsComponent toolbarsComponent = new ToolbarsComponent();
    for (FieldPanel fieldPanel : fieldPanels.list()) {
      int height = 28;
      JToolBar jToolBar = ToolbarsComponent.createJToolBar(FlowLayout.RIGHT);
      {
        Field field = fieldPanel.fieldWrap().getField();
        String string = field.getName();
        {
          FieldLabel fieldLabel = field.getAnnotation(FieldLabel.class);
          if (Objects.nonNull(fieldLabel))
            string = fieldLabel.text();
        }
        JLabel jLabel = new JLabel(string);
        jLabel.setToolTipText(StaticHelper.getToolTip(field));
        jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, height));
        jToolBar.add(jLabel);
      }
      JComponent jComponent = fieldPanel.getJComponent();
      Dimension dimension = jComponent.getPreferredSize();
      dimension.width = Math.max(dimension.width, 100);
      jComponent.setPreferredSize(dimension);
      toolbarsComponent.addPair(jToolBar, jComponent, height);
    }
    return toolbarsComponent;
  }

  /***************************************************/
  private final FieldPanels fieldPanels;
  private final ToolbarsComponent toolbarsComponent;
  private final JScrollPane jScrollPane;

  private ConfigPanel(Object object) {
    fieldPanels = FieldPanels.of(object);
    toolbarsComponent = build(fieldPanels);
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
