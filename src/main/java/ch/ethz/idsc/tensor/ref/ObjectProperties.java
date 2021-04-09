// code by jph
package ch.ethz.idsc.tensor.ref;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Import;

/** manages configurable parameters by introspection of a given instance
 * 
 * values of non-final, non-static, non-transient but public members of type
 * {@link Tensor}, {@link Scalar}, {@link String}, {@link File}, {@link Boolean},
 * {@link Enum}
 * are stored in, and retrieved from files in the {@link Properties} format */
public class ObjectProperties {
  private static final int MASK_FILTER = Modifier.PUBLIC;
  private static final int MASK_TESTED = MASK_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

  /** @param object non-null
   * @return
   * @throws Exception if given object is null */
  public static ObjectProperties wrap(Object object) {
    return new ObjectProperties(object);
  }

  /***************************************************/
  private final Object object;

  private ObjectProperties(Object object) {
    this.object = Objects.requireNonNull(object);
  }

  /** @return map of tracked fields of given object
   * in the order in which they appear top to bottom in the class, superclass first */
  public Map<Field, FieldType> fields() {
    Deque<Class<?>> deque = new ArrayDeque<>();
    for (Class<? extends Object> cls = object.getClass(); //
        !cls.equals(Object.class); //
        cls = cls.getSuperclass())
      deque.push(cls);
    Map<Field, FieldType> map = new LinkedHashMap<>();
    while (!deque.isEmpty())
      for (Field field : deque.pop().getDeclaredFields())
        if (isModified(field)) {
          Class<?> cls = field.getType();
          Optional<FieldType> optional = Stream.of(FieldType.values()) //
              .filter(type -> type.isTracking(cls)) //
              .findFirst();
          if (optional.isPresent())
            map.put(field, optional.get());
        }
    return map;
  }

  /** @param field
   * @return whether field is public, non final, non static, non transient */
  /* package */ static final boolean isModified(Field field) {
    return (field.getModifiers() & MASK_TESTED) == MASK_FILTER;
  }

  /** @param properties
   * @param object with fields to be assigned according to given properties
   * @return given object
   * @throws Exception if properties is null */
  @SuppressWarnings("unchecked")
  public <T> T set(Properties properties) {
    fields().entrySet().forEach(entry -> {
      Field field = entry.getKey();
      FieldType fieldType = entry.getValue();
      String string = properties.getProperty(field.getName());
      if (Objects.nonNull(string))
        try {
          field.set(object, fieldType.toObject(field.getType(), string));
        } catch (Exception exception) {
          exception.printStackTrace();
        }
    });
    return (T) object;
  }

  /** @param object
   * @return properties with fields of given object as keys mapping to values as string expression */
  public Properties get() {
    Properties properties = new Properties();
    consume(properties::setProperty);
    return properties;
  }

  /***************************************************/
  /** values defined in properties file are assigned to fields of given object
   * 
   * @param file properties
   * @param object
   * @return object with fields updated from properties file
   * @throws IOException
   * @throws FileNotFoundException */
  public void load(File file) throws FileNotFoundException, IOException {
    set(Import.properties(file));
  }

  /** @param file properties
   * @return object with fields updated from properties file if loading was successful */
  @SuppressWarnings("unchecked")
  public <T> T tryLoad(File file) {
    try {
      load(file);
    } catch (Exception exception) {
      // ---
    }
    return (T) object;
  }

  /** store tracked fields of given object in given file
   * 
   * @param file properties
   * @param object
   * @throws IOException */
  public void save(File file) throws IOException {
    Files.write(file.toPath(), (Iterable<String>) strings()::iterator);
  }

  /** @param file
   * @return true if saving to given file was successful, false otherwise */
  public boolean trySave(File file) {
    try {
      save(file);
      return true;
    } catch (Exception exception) {
      // ---
    }
    return false;
  }

  public List<String> strings() {
    List<String> list = new LinkedList<>();
    consume((field, value) -> list.add(field + "=" + value));
    return list;
  }

  // helper function
  private void consume(BiConsumer<String, String> biConsumer) {
    fields().keySet().forEach(field -> {
      try {
        Object value = field.get(object); // may throw Exception
        if (Objects.nonNull(value))
          biConsumer.accept(field.getName(), FieldType.toString(value));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });
  }

  public boolean setIfValid(Field field, FieldType fieldType, String string) {
    try {
      Object value = fieldType.toObject(field.getType(), string);
      if (fieldType.isValidValue(field, value)) {
        field.set(object, value);
        return true;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return false;
  }
}
