// code by jph
package ch.alpine.bridge.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.filechooser.FileFilter;

import ch.alpine.bridge.ref.ann.FieldExistingDirectory;
import ch.alpine.bridge.ref.ann.FieldExistingFile;
import ch.alpine.bridge.ref.ann.FieldFileExtension;
import ch.alpine.bridge.ref.ann.FieldFileExtensions;

/* package */ class FileFieldWrap extends BaseFieldWrap {
  private final FieldExistingDirectory fieldExistingDirectory;
  private final FieldExistingFile fieldExistingFile;
  private final FileFilter[] filters;

  public FileFieldWrap(Field field) {
    super(field);
    fieldExistingDirectory = field.getAnnotation(FieldExistingDirectory.class);
    fieldExistingFile = field.getAnnotation(FieldExistingFile.class);
    FieldFileExtension.List fieldExtensionFiles = field.getAnnotation(FieldFileExtension.List.class);
    filters = Objects.nonNull(fieldExtensionFiles) //
        ? Arrays.stream(fieldExtensionFiles.value()).map(FieldFileExtensions::of).toArray(FileFilter[]::new) //
        : new FileFilter[] {};
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
    File file = (File) Objects.requireNonNull(value);
    // ---
    if (Objects.nonNull(fieldExistingDirectory) && !file.isDirectory())
      return false;
    // ---
    if (Objects.nonNull(fieldExistingFile) && !file.isFile())
      return false;
    // ---
    if (filters.length > 0 && Arrays.stream(filters).noneMatch(filter -> filter.accept(file)))
      return false;
    // ---
    return true;
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new FilePanel(this, (File) value, filters);
  }
}
