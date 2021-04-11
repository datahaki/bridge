// code by jph
package ch.ethz.idsc.tensor.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class FileFieldWrap extends BaseFieldWrap {
  public FileFieldWrap(Field field) {
    super(field);
  }

  @Override
  public Object toValue(String string) {
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
