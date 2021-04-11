// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
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
          .map(Enum.class::cast) //
          .filter(object -> object.name().equals(string)) //
          .findFirst() //
          .orElse(null);
    }

    @Override
    public String toString(Object object) {
      return ((Enum<?>) object).name();
    }
  },
  FILE(File.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return new File(string);
    }

    @Override
    public boolean isValidValue(Field field, Object object) {
      if (object instanceof File) {
        File file = (File) object;
        {
          FieldExistingDirectory fieldExistingDirectory = field.getAnnotation(FieldExistingDirectory.class);
          if (Objects.nonNull(fieldExistingDirectory)) {
            return file.isDirectory();
          }
        }
        return true;
      }
      return false;
    }
  },
  TENSOR(Tensor.class::equals) {
    @Override
    public Object toObject(Class<?> cls, String string) {
      return Tensors.fromString(string);
    }

    @Override
    public boolean isValidValue(Field field, Object object) {
      return object instanceof Tensor //
          && !StringScalarQ.any((Tensor) object);
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
          if (Objects.nonNull(fieldClip))
            try {
              Clip clip = TensorReflection.clip(fieldClip);
              if (clip.isOutside(UnitSystem.SI().apply(scalar)))
                return false;
            } catch (Exception exception) {
              // System.err.println("unit incompatible " + clip + " " + scalar);
              return false;
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

    @Override
    public String toString(Object object) {
      return ColorFormat.toVector((Color) object).toString();
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

  /** @param cls typically {@link Field#getType()}
   * @param string
   * @return null if string cannot be parsed (to a valid object?) */
  /* package */ abstract Object toObject(Class<?> cls, String string);

  public String toString(Object object) {
    return object.toString();
  }

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

  /***************************************************/
  public static Optional<FieldType> getFieldType(Class<?> cls) {
    return Stream.of(FieldType.values()) //
        .filter(fieldType -> fieldType.isTracking(cls)) //
        .findFirst();
  }
}