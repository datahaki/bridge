// code by jph
package ch.alpine.java.ref.gui;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldInteger;

public enum FieldToolTip {
  ;
  public static String of(Field field) {
    List<String> list = new LinkedList<>();
    {
      FieldInteger fieldIntegerQ = field.getAnnotation(FieldInteger.class);
      if (Objects.nonNull(fieldIntegerQ)) {
        list.add("integer");
      }
    }
    {
      FieldClip fieldClip = field.getAnnotation(FieldClip.class);
      if (Objects.nonNull(fieldClip)) {
        list.add("min=" + fieldClip.min());
        list.add("max=" + fieldClip.max());
      }
    }
    return list.isEmpty() //
        ? null
        : list.stream().collect(Collectors.joining(", ", "", ""));
  }
}
