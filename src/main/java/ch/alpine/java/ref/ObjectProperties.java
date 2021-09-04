// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;

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
   * 
   * @param object
   * @param file properties
   * @throws IOException */
  public static void save(Object object, File file) throws IOException {
    Files.write(file.toPath(), (Iterable<String>) ObjectFieldList.of(object)::iterator);
  }

  /** @param object
   * @param file
   * @return true if saving to given file was successful, false otherwise */
  public static void trySave(Object object, File file) {
    try {
      save(object, file);
    } catch (Exception exception) {
      // ---
    }
  }

  /** @param
   * @param file properties
   * @return object with fields updated from properties file if loading was successful */
  public static <T> T tryLoad(T object, File file) {
    try {
      set(object, Import.properties(file));
    } catch (Exception exception) {
      // ---
    }
    return object;
  }

  public static <T> T set(T object, Properties properties) {
    ObjectFields.of(object, new ObjectFieldImport(properties));
    return object;
  }

  public static void setIfValid(FieldWrap fieldWrap, Object object, String string) {
    try {
      Object value = fieldWrap.toValue(string);
      if (Objects.nonNull(value) && fieldWrap.isValidValue(value)) // otherwise retain current assignment
        fieldWrap.getField().set(object, value);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
