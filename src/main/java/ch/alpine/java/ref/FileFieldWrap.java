// code by jph
package ch.alpine.java.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.java.ref.gui.FieldPanel;

public class FileFieldWrap extends BaseFieldWrap {
  private final FieldExistingDirectory fieldExistingDirectory;
  private final FieldExistingFile fieldExistingFile;

  public FileFieldWrap(Field field) {
    super(field);
    fieldExistingDirectory = field.getAnnotation(FieldExistingDirectory.class);
    fieldExistingFile = field.getAnnotation(FieldExistingFile.class);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return new File(string);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    File file = (File) value;
    // ---
    if (Objects.nonNull(fieldExistingDirectory) && !file.isDirectory())
      return false;
    // ---
    if (Objects.nonNull(fieldExistingFile) && !file.isFile())
      return false;
    // ---
    return true;
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object value) {
    return new FilePanel(this, (File) value);
  }
}
