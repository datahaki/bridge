// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.BiConsumer;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Import;

/** manages configurable parameters by introspection of a given instance
 * 
 * values of non-final, non-static, non-transient but public members of type
 * {@link Tensor}, {@link Scalar}, {@link String}, {@link File}, {@link Boolean},
 * {@link Enum}, {@link Color}
 * are stored in, and retrieved from files in the {@link Properties} format
 * 
 * the listed of supported types can be extended, see {@link FieldWraps}
 * 
 * Hint: the implementation does not assign null values to members. in case
 * of a parse failure, or invalid assignment, the preset/default/current
 * value is retained. */
public class ObjectProperties {
  /** @param object non-null
   * @return
   * @throws Exception if given object is null */
  public static ObjectProperties wrap(Object object) {
    return new ObjectProperties(object);
  }

  /***************************************************/
  private final Object object;
  private List<FieldWrap> fields;

  private ObjectProperties(Object object) {
    this.object = object;
    fields = StaticHelper.CACHE.apply(object.getClass());
  }

  /** @return map of tracked fields of given object
   * in the order in which they appear top to bottom in the class, superclass first */
  public List<FieldWrap> fields() {
    return fields;
  }

  /** @param properties
   * @param object with fields to be assigned according to given properties
   * @return given object
   * @throws Exception if properties is null */
  @SuppressWarnings("unchecked")
  public <T> T set(Properties properties) {
    for (FieldWrap fieldWrap : fields) {
      String string = properties.getProperty(fieldWrap.getField().getName());
      if (Objects.nonNull(string))
        setIfValid(fieldWrap, string);
    }
    return (T) object;
  }

  public void setIfValid(FieldWrap fieldWrap, String string) {
    try {
      Object value = fieldWrap.toValue(string);
      if (Objects.nonNull(value) && fieldWrap.isValidValue(value)) // otherwise retain current assignment
        fieldWrap.getField().set(object, value);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /** @param object
   * @return properties with fields of given object as keys mapping to values as string expression */
  public Properties createProperties() {
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
    for (FieldWrap fieldWrap : fields) {
      Field field = fieldWrap.getField();
      try {
        Object value = field.get(object); // may throw Exception
        if (Objects.nonNull(value))
          biConsumer.accept(field.getName(), fieldWrap.toString(value));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
}
