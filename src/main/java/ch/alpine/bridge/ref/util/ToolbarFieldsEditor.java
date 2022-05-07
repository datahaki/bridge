// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldToolTip;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ObjectFieldVisitor;
import ch.alpine.bridge.ref.ObjectFields;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldLabels;
import ch.alpine.tensor.sca.Clip;

public class ToolbarFieldsEditor extends FieldsEditor {
  private static final int CARET_WIDTH = 2;
  // private static final float FONT_REDUCE = 0.9f;

  /** @param object
   * @param jToolBar
   * @return fields editor */
  public static FieldsEditor add(Object object, JToolBar jToolBar) {
    FieldsEditor fieldsEditor = new ToolbarFieldsEditor(object, jToolBar);
    for (FieldPanel fieldPanel : fieldsEditor.list()) {
      FieldWrap fieldWrap = fieldPanel.fieldWrap();
      JComponent jComponent = fieldPanel.getJComponent();
      Field field = fieldWrap.getField();
      // System.out.println("HE");
      // if (false)
      // if (field.getType().equals(Scalar.class))
      System.out.println("---");
      System.out.println(fieldWrap.getField().getName());
      if (false)
        if (jComponent instanceof JTextField) {
          FieldClip fieldClip = field.getAnnotation(FieldClip.class);
          FontMetrics fontMetrics = jComponent.getFontMetrics(jComponent.getFont());
          int max = fontMetrics.stringWidth("XXX");
          if (Objects.nonNull(fieldClip)) {
            Clip clip = FieldClips.of(fieldClip);
            max = Math.max(max, Math.max( //
                fontMetrics.stringWidth(clip.min().toString()), //
                fontMetrics.stringWidth(clip.max().toString())));
          }
          Border border = jComponent.getBorder();
          Insets insets = Objects.nonNull(border) //
              ? border.getBorderInsets(jComponent)
              : new Insets(0, 0, 0, 0);
          max += insets.left + insets.right + CARET_WIDTH;
          Dimension dimension = jComponent.getPreferredSize();
          // look and fields should not define a minimum preferred width
          dimension.width = max;
          System.out.println(dimension);
          jComponent.setPreferredSize(dimension);
          jComponent.setMinimumSize(dimension);
        } else {
          System.err.println(jComponent);
        }
    }
    return fieldsEditor;
  }

  private class Visitor implements ObjectFieldVisitor {
    private final JToolBar jToolBar;

    public Visitor(JToolBar jToolBar) {
      this.jToolBar = jToolBar;
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      Field field = fieldWrap.getField();
      String text = FieldLabels.of(key, field, null);
      boolean isBoolean = field.getType().equals(Boolean.class);
      boolean isFuse = Objects.nonNull(field.getAnnotation(FieldFuse.class));
      FieldPanel fieldPanel = register(isBoolean && !isFuse //
          ? new TogglePanel(fieldWrap, text, (Boolean) value)
          : fieldWrap.createFieldPanel(object, value), fieldWrap, object);
      JComponent jComponent = layout(field, fieldPanel.getJComponent());
      if (field.getType().equals(Enum.class)) {
        jComponent.setToolTipText(text);
      } else //
        if (!isBoolean) {
          JLabel jLabel = new JLabel(text + " ");
          Font font = jLabel.getFont();
          final int _style = font.getStyle();
          int style = _style;
          if (_style == Font.BOLD) // for default look and feel
            style = Font.PLAIN;
          if (_style == Font.PLAIN)
            style = Font.ITALIC;
          jLabel.setFont(font.deriveFont(style));
          jLabel.setToolTipText(FieldToolTip.of(field));
          jToolBar.add(jLabel);
        }
      jToolBar.add(jComponent);
      jToolBar.addSeparator();
    }
  }

  private ToolbarFieldsEditor(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }
}
