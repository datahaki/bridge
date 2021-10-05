// code by jph
package ch.alpine.java.ref.gui;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;

import ch.alpine.java.ref.ann.FieldPreferredWidth;

/* package */ enum StaticHelper {
  ;
  public static JComponent layout(Field field, JComponent jComponent) {
    FieldPreferredWidth fieldPreferredWidth = field.getAnnotation(FieldPreferredWidth.class);
    if (Objects.nonNull(fieldPreferredWidth)) {
      Dimension dimension = jComponent.getPreferredSize();
      dimension.width = Math.max(dimension.width, fieldPreferredWidth.width());
      jComponent.setPreferredSize(dimension);
    }
    return jComponent;
  }
}
