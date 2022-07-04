// code by jph
package ch.alpine.bridge.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.filechooser.FileFilter;

import ch.alpine.bridge.ref.ann.FieldExistingDirectory;
import ch.alpine.bridge.ref.ann.FieldExistingFile;
import ch.alpine.bridge.ref.ann.FieldFileExtension;
import ch.alpine.bridge.ref.ann.FieldFileExtensions;

/* package */ class FileFieldWrap extends BaseFieldWrap {
  private final FieldExistingDirectory fieldExistingDirectory;
  private final FieldExistingFile fieldExistingFile;
  private final List<FileFilter> fileFilters;

  public FileFieldWrap(Field field) {
    super(field);
    fieldExistingDirectory = field.getAnnotation(FieldExistingDirectory.class);
    fieldExistingFile = field.getAnnotation(FieldExistingFile.class);
    fileFilters = Arrays.stream(field.getAnnotationsByType(FieldFileExtension.class)) //
        .map(FieldFileExtensions::of) //
        .collect(Collectors.toList());
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
    if (!fileFilters.isEmpty() && fileFilters.stream().noneMatch(filter -> filter.accept(file)))
      return false;
    // ---
    return true;
  }

  @Override // from FieldWrap
  public List<String> options(Object object) {
    return List.of();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new FilePanel(this, (File) value, fileFilters);
  }
}
