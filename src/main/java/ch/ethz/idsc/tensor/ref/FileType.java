package ch.ethz.idsc.tensor.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

public class FileType implements FieldIf {
  @Override
  public boolean isTracking(Class<?> cls) {
    return File.class.equals(cls);
  }

  @Override
  public Object toObject(Class<?> cls, String string) {
    return new File(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
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
}
