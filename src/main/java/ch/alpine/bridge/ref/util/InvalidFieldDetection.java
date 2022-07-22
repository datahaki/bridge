// code by jph
package ch.alpine.bridge.ref.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldExistingDirectory;
import ch.alpine.bridge.ref.ann.FieldExistingFile;
import ch.alpine.bridge.ref.ann.FieldFileExtension;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.FieldSkipInvalidCheck;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;

/** Remark:
 * field with the modifier `transient` are excluded from inspection */
public class InvalidFieldDetection extends ObjectFieldIo {
  /** @param object
   * @return */
  public static List<FieldValueContainer> of(Object object) {
    InvalidFieldDetection invalidFieldCollection = new InvalidFieldDetection();
    ObjectFields.of(object, invalidFieldCollection);
    return invalidFieldCollection.list();
  }

  /** @param object
   * @return */
  public static boolean isEmpty(Object object) {
    return of(object).isEmpty();
  }

  // ---
  private final List<FieldValueContainer> list = new LinkedList<>();

  /** @param field
   * @param annotationClass
   * @param cls
   * @return whether annotationClass can be combined with field */
  private static <T extends Annotation> boolean require(Field field, Class<T> annotationClass, Class<?> cls) {
    Class<?> class_field = field.getType();
    T annotation = field.getAnnotation(annotationClass);
    return Objects.isNull(annotation) || class_field.equals(cls);
  }

  @Override // from ObjectFieldVisitor
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    Field field = fieldWrap.getField();
    boolean valid = true;
    {
      valid &= require(field, FieldClip.class, Scalar.class);
      valid &= require(field, FieldFuse.class, Boolean.class);
      valid &= require(field, FieldInteger.class, Scalar.class);
      valid &= require(field, FieldSlider.class, Scalar.class);
      valid &= require(field, FieldExistingDirectory.class, File.class);
      valid &= require(field, FieldExistingFile.class, File.class);
      valid &= require(field, FieldFileExtension.class, File.class);
    }
    try {
      fieldWrap.options(object);
    } catch (Exception exception) {
      valid = false;
    }
    {
      FieldSelectionArray fieldSelectionArray = field.getAnnotation(FieldSelectionArray.class);
      if (Objects.nonNull(fieldSelectionArray))
        valid &= Arrays.stream(fieldSelectionArray.value()) //
            .map(fieldWrap::toValue) //
            .allMatch(Objects::nonNull);
    }
    {
      FieldSelectionCallback fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
      if (Objects.nonNull(fieldSelectionCallback))
        try {
          Method method = object.getClass().getMethod(fieldSelectionCallback.value());
          {
            ReflectionMarker reflectionMarker = method.getAnnotation(ReflectionMarker.class);
            if (Objects.isNull(reflectionMarker)) {
              System.err.println("not annotated: " + method);
              valid = false;
            }
          }
          Object invoke = method.invoke(object);
          valid &= invoke instanceof List<?> && ((List<?>) list).stream().allMatch(Objects::nonNull);
        } catch (Exception exception) {
          valid = false;
        }
    }
    {
      FieldSkipInvalidCheck fieldSkipInvalidCheck = field.getAnnotation(FieldSkipInvalidCheck.class);
      if (Objects.isNull(fieldSkipInvalidCheck))
        try {
          valid &= Objects.nonNull(value) && fieldWrap.isValidValue(value);
        } catch (Exception exception) {
          valid = false;
        }
    }
    if (!valid)
      list.add(new FieldValueContainer(key, fieldWrap, object, value));
  }

  public List<FieldValueContainer> list() {
    return list;
  }
}
