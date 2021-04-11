// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ref.FieldLabel;

/** component that generically inspects a given object for fields of type
 * {@link Tensor} and {@link Scalar}. For each such field, a text field
 * is provided that allows the modification of the value. */
public enum ParametersComponent {
  ;
  /** @param fieldPanels
   * @return */
  public static ToolbarsComponent of(FieldPanels fieldPanels) {
    ToolbarsComponent toolbarsComponent = new ToolbarsComponent();
    for (FieldPanel fieldPanel : fieldPanels.list()) {
      int height = 28;
      JToolBar jToolBar = new JToolBar();
      {
        Field field = fieldPanel.fieldType().getField();
        jToolBar.setFloatable(false);
        jToolBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 0));
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
}
