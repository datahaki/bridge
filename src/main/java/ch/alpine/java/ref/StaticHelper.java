// code by jph
package ch.alpine.java.ref;

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
      // for instance, a JSlider has a default preferred width of 200
      dimension.width = fieldPreferredWidth.width();
      jComponent.setPreferredSize(dimension);
    }
    return jComponent;
  }
}
