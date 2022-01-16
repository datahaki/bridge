// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.Import;

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
  /** list of "key=value" of tracked fields of given object
   * 
   * @param object
   * @return list of strings each of the form "key=value" */
  public static List<String> list(Object object) {
    ObjectFieldList objectFieldList = new ObjectFieldList();
    ObjectFields.of(object, objectFieldList);
    return objectFieldList.list;
  }

  private static class ObjectFieldList implements ObjectFieldVisitor {
    private final List<String> list = new LinkedList<>();

    @Override
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      if (Objects.nonNull(value))
        list.add(key + "=" + fieldWrap.toString(value));
    }
  }

  // ---
  /** store tracked fields of given object in given file
   * 
   * @param object
   * @param file properties
   * @throws IOException */
  public static void save(Object object, File file) throws IOException {
    Files.write(file.toPath(), (Iterable<String>) list(object)::iterator);
  }

  /** store tracked fields of given object in given file
   * 
   * @param object
   * @param file properties
   * @return true if saving to given file was successful, false otherwise */
  public static boolean trySave(Object object, File file) {
    try {
      save(object, file);
      return true;
    } catch (Exception exception) {
      // ---
    }
    return false;
  }

  // ---
  /** @param object
   * @return new instance of {@link Properties} */
  public static Properties properties(Object object) {
    ObjectFieldExport objectFieldExport = new ObjectFieldExport();
    ObjectFields.of(object, objectFieldExport);
    return objectFieldExport.properties;
  }

  private static class ObjectFieldExport implements ObjectFieldVisitor {
    private final Properties properties = new Properties();

    @Override // from ObjectFieldVisitor
    public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
      if (Objects.nonNull(value))
        properties.setProperty(prefix, fieldWrap.toString(value));
    }
  }

  // ---
  /** @param object
   * @param properties
   * @return object with fields modified based on properties. In particular,
   * if properties is empty then the object will not be modified at all. */
  public static <T> T set(T object, Properties properties) {
    ObjectFields.of(object, new ObjectFieldImport(properties));
    return object;
  }

  private static class ObjectFieldImport implements ObjectFieldVisitor {
    private final Properties properties;

    public ObjectFieldImport(Properties properties) {
      this.properties = properties;
    }

    @Override
    public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
      String string = properties.getProperty(prefix);
      if (Objects.nonNull(string))
        fieldWrap.setIfValid(object, string);
    }
  }

  // ---
  /** @param object
   * @return */
  public static String string(Object object) {
    return list(object).stream().collect(Collectors.joining("\n"));
  }

  /** @param object
   * @return string
   * @throws IOException */
  public static void fromString(Object object, String string) throws IOException {
    Properties properties = new Properties();
    try (StringReader stringReader = new StringReader(string)) {
      properties.load(stringReader);
    }
    set(object, properties);
  }

  // ---
  /** @param object
   * @param file
   * @return
   * @throws FileNotFoundException
   * @throws IOException */
  public static void load(Object object, File file) throws FileNotFoundException, IOException {
    set(object, Import.properties(file));
  }

  /** @param object
   * @param file properties
   * @return object with fields updated from properties file if loading was successful */
  public static <T> T tryLoad(T object, File file) {
    try {
      load(object, file);
    } catch (Exception exception) {
      // ---
    }
    return object;
  }
}
