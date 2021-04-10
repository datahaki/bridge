// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorFormat;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import ch.ethz.idsc.tensor.qty.UnitSystem;
import ch.ethz.idsc.tensor.sca.Clip;

// TODO notify if input is bad, since field will be omitted from gui
public enum FieldType {
  STRING(String.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return string;
    }
  },
  BOOLEAN(Boolean.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return BooleanParser.orNull(string);
    }
  },
  ENUM(Enum.class::isAssignableFrom) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return Stream.of(cls.getEnumConstants()) //
          .filter(object -> ((Enum<?>) object).name().equals(string)) //
          .findFirst() //
          .orElse(null);
    }
  },
  FILE(File.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return new File(string);
    }
  },
  TENSOR(Tensor.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return Tensors.fromString(string);
    }

    @Override
    public boolean isValidValue(Field field, Object object) {
      if (object instanceof Tensor && //
      !StringScalarQ.any((Tensor) object)) {
        // Tensor tensor = (Tensor) object;
        // {
        // FieldColor fieldColor = field.getAnnotation(FieldColor.class);
        // if (Objects.nonNull(fieldColor)) {
        // try {
        // ColorFormat.toColor(tensor);
        // } catch (Exception exception) {
        // return false;
        // }
        // }
        // }
        return true;
      }
      return false;
    }
  },
  SCALAR(Scalar.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return Scalars.fromString(string);
    }

    @Override
    public boolean isValidValue(Field field, Object object) {
      if (object instanceof Scalar) {
        Scalar scalar = (Scalar) object;
        if (StringScalarQ.of(scalar))
          return false;
        {
          FieldIntegerQ fieldIntegerQ = field.getAnnotation(FieldIntegerQ.class);
          if (Objects.nonNull(fieldIntegerQ))
            if (!IntegerQ.of(scalar))
              return false;
        }
        {
          FieldClip fieldClip = field.getAnnotation(FieldClip.class);
          if (Objects.nonNull(fieldClip)) {
            Clip clip = TensorReflection.clip(fieldClip);
            try {
              if (clip.isOutside(UnitSystem.SI().apply(scalar)))
                return false;
            } catch (Exception exception) {
              // System.err.println("unit incompatible " + clip + " " + scalar);
              return false;
            }
          }
        }
        return true;
      }
      return false;
    }
  },
  COLOR(Color.class::equals) {
    @Override
    Object toObject(Class<?> cls, String string) {
      try {
        return ColorFormat.toColor(Tensors.fromString(string));
      } catch (Exception exception) {
        // ---
      }
      return null;
    }
  }, //
  ;

  private final Predicate<Class<?>> predicate;

  private FieldType(Predicate<Class<?>> predicate) {
    this.predicate = predicate;
  }

  /* package */ final boolean isTracking(Class<?> cls) {
    return predicate.test(cls);
  }

  /** @param cls
   * @param string
   * @return null if string cannot be parsed (to a valid object?) */
  /* package */ abstract Object toObject(Class<?> cls, String string);

  public final boolean isValidString(Field field, String string) {
    return isValidValue(field, toObject(field.getType(), string));
  }

  /** implementations may override method
   * 
   * @param field parameter in order to access annotations
   * @param object
   * @return whether object is non-null and object is of correct type and
   * satisfies requirements specified by annotations */
  public boolean isValidValue(Field field, Object object) {
    return Objects.nonNull(object) //
        && predicate.test(object.getClass()); // default implementation
  }

  /** @param object
   * @return */
  // TODO not elegant
  public static String toString(Object object) {
    if (Enum.class.isAssignableFrom(object.getClass()))
      return ((Enum<?>) object).name();
    if (Color.class.equals(object.getClass()))
      return ColorFormat.toVector((Color) object).toString();
    return object.toString();
  }
}