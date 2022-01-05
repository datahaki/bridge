// code by jph
package ch.alpine.java.ref.ann;

import java.lang.reflect.Field;
import java.util.Objects;

public enum FieldLabels {
  ;
  /** @param key
   * @param field
   * @param index non-null iff field represents array, or list
   * @return */
  public static String of(String key, Field field, Integer index) {
    {
      FieldLabel fieldLabel = field.getAnnotation(FieldLabel.class);
      if (Objects.isNull(index)) // base case
        return Objects.isNull(fieldLabel) //
            ? key.substring(key.lastIndexOf('.') + 1) // default choice: label text as in java code
            : fieldLabel.value();
      // ---
      // below here, index is guaranteed to be non-null
      if (Objects.nonNull(fieldLabel))
        try {
          // we make the assumption that the user wants to see the counter starting from 1
          return String.format(fieldLabel.value(), index + 1);
        } catch (Exception exception) {
          // in case field label is not suitable for use with String#format
        }
    }
    // ---
    {
      FieldLabelArray fieldLabelArray = field.getAnnotation(FieldLabelArray.class);
      if (Objects.nonNull(fieldLabelArray)) {
        String[] text = fieldLabelArray.value();
        if (index < text.length)
          return text[index];
      }
    }
    return key.substring(key.lastIndexOf('.') + 1);
  }
}
