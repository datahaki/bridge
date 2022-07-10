// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldWraps;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.Import;

/** manages configurable parameters by introspection of a given instance
 * 
 * values of non-final, non-static, non-transient but public members of type
 * {@link Tensor}, {@link Scalar}, {@link String}, {@link File}, {@link Boolean},
 * {@link Enum}, {@link Color}, as well as nested parameters or arrays/lists
 * thereof are stored in, and retrieved from files in the {@link Properties} format.
 * 
 * the listed of supported types can be extended, see {@link FieldWraps}
 * 
 * Hint: the implementation does not assign null values to members. in case
 * of a parse failure, or invalid assignment, the preset/default/current
 * value is retained. */
public class ObjectProperties {
  /** charset UTF-8 guarantees the storage and loading of special
   * characters such as Chinese characters.
   * As of Java 18, the default charset is UTF-8. */
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  /** function is used to store in properties-file
   * and also to compile a list of strings, or a single string expression
   * 
   * @param prefix
   * @param value_toString
   * @return conceptually the function returns prefix=value_toString
   * but uses the formatting defined by {@link Properties} */
  private static String line(String prefix, String value_toString) {
    boolean escUnicode = false;
    String key = PropertiesExt.saveConvert(prefix, true, escUnicode);
    String val = PropertiesExt.saveConvert(value_toString, false, escUnicode);
    return key + "=" + val;
  }

  /** store tracked fields of given object in given file
   * in the {@link Properties}-format.
   * The ordering of the key-value pairs in the file
   * is as they are visited by {@link ObjectFields}.
   * 
   * @param object
   * @param file properties
   * @throws IOException */
  public static void save(Object object, File file) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, CHARSET))) {
      ObjectFieldVisitor objectFieldVisitor = new ObjectFieldIo() {
        @Override // from ObjectFieldVisitor
        public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
          if (Objects.nonNull(value))
            try {
              bufferedWriter.write(line(prefix, fieldWrap.toString(value)));
              bufferedWriter.newLine();
            } catch (Exception exception) {
              throw new RuntimeException(exception);
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
   * @param file properties
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   * @see Properties */
  public static void load(Object object, File file) throws FileNotFoundException, IOException {
    set(object, Import.properties(file, CHARSET));
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

  // ---
  /** function modifies fields of given object based on given properties
   * 
   * Remark: API intentionally does not provide
   * a function that creates {@link Properties} from given object
   * 
   * @param object
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

    @Override // from ObjectFieldVisitor
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

    @Override // from ObjectFieldVisitor
    public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
      if (Objects.nonNull(value))
        list.add(line(prefix, fieldWrap.toString(value)));
    }
  }

  /** @param object
   * @return single string expression that encodes the content of given object */
  public static String join(Object object) {
    return list(object).stream().collect(Collectors.joining("\n"));
  }

  /** function assigns the fields in given object based on the content
   * specification given by string
   * 
   * @param object to be assigned the values specified in given string
   * @param string single string expression that encodes the content of given object
   * @see {@link #join(Object)} */
  public static void part(Object object, String string) {
    try (Reader reader = new StringReader(string)) {
      Properties properties = new Properties();
      properties.load(reader);
      set(object, properties);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
