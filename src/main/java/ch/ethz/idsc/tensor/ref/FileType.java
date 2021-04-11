// code by jph
package ch.ethz.idsc.tensor.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;
import ch.ethz.idsc.tensor.ref.gui.FilePanel;

public class FileType extends FieldBase {
  public FileType(Field field) {
    super(field);
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return File.class.equals(cls);
  }

  @Override
  public Object toObject(String string) {
    return new File(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Object value) {
    File file = (File) value;
    {
      FieldExistingDirectory fieldExistingDirectory = field.getAnnotation(FieldExistingDirectory.class);
      if (Objects.nonNull(fieldExistingDirectory)) {
        return file.isDirectory();
      }
    }
    return true;
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new FilePanel(this, (File) value);
  }
}
