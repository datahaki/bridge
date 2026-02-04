// code by jph
package sys;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import ch.alpine.tensor.ext.FileExtension;

public class Filename implements Comparable<Filename>, Serializable {
  private final File file;
  private final String extension;
  private final String title;

  public Filename(File file) {
    this.file = file;
    extension = FileExtension.of(file);
    String string = file.getName();
    title = extension.isEmpty() //
        ? string
        : string.substring(0, string.length() - extension.length() - 1);
  }

  public File file() {
    return file;
  }

  public String extension() {
    return extension;
  }

  public String title() {
    return title;
  }

  public boolean hasExtension(String string) {
    return extension.equalsIgnoreCase(string);
  }

  /** @param string non-null
   * @return */
  public File withExtension(String string) {
    return new File(file.getParentFile(), title + FileExtension.DOT + Objects.requireNonNull(string));
  }

  public File asDirectory() {
    return new File(file.getParentFile(), title);
  }

  @Override
  public int compareTo(Filename filename) {
    return file.compareTo(filename.file);
  }

  @Override
  public int hashCode() {
    return file.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof Filename filename //
        && file.equals(filename.file);
  }
}
