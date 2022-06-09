// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
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
  /** store tracked fields of given object in given file
   * in the {@link Properties}-format.
   * The ordering of the key-value pairs in the file
   * is as they are visited by {@link ObjectFields}.
   * 
   * @param object
   * @param file properties
   * @throws IOException */
  public static void save(Object object, File file) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
      ObjectFieldVisitor objectFieldVisitor = new ObjectFieldIo() {
        @Override // from ObjectFieldVisitor
        public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
          if (Objects.nonNull(value)) {
            boolean escUnicode = false;
            String key = PropertiesExt.saveConvert(prefix, true, escUnicode);
            String val = PropertiesExt.saveConvert(fieldWrap.toString(value), false, escUnicode);
            try {
              bufferedWriter.write(key + "=" + val);
              bufferedWriter.newLine();
            } catch (Exception exception) {
              throw new RuntimeException();
            }
          }
        }
      };
      ObjectFields.of(object, objectFieldVisitor);
    }
  }

  /** store tracked fields of given object in given file
   * in the {@link Properties}-format.
   * The ordering of the key-value pairs in the file
   * is as they are visited by {@link ObjectFields}.
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
   * @param properties
   * @return object with fields modified based on properties. In particular,
   * if properties is empty then the object will not be modified at all. */
  public static <T> T set(T object, Properties properties) {
    ObjectFields.of(object, new ObjectFieldImport(properties));
    return object;
  }

  private static class ObjectFieldImport extends ObjectFieldIo {
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
  /** list of "key=value" of tracked fields of given object
   * 
   * @param object
   * @return list of strings each of the form "key=value" */
  public static List<String> list(Object object) {
    ObjectFieldList objectFieldList = new ObjectFieldList();
    ObjectFields.of(object, objectFieldList);
    return objectFieldList.list;
  }

  private static class ObjectFieldList extends ObjectFieldIo {
    private final List<String> list = new LinkedList<>();

    @Override
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      if (Objects.nonNull(value))
        list.add(key + "=" + fieldWrap.toString(value));
    }
  }

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
