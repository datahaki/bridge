// code by jph
package ch.alpine.bridge.io;

import java.io.File;

import ch.alpine.bridge.ref.util.ObjectProperties;

/** manage operations of ObjectProperties on a local installation
 * suitable for singleton parameter files, for instance editor */
public final class ResourceLocator {
  public static final String FILE_EXTENSION = ".properties";
  // ---
  private final File base;

  /** @param base directory which will be created if necessary */
  public ResourceLocator(File base) {
    this.base = base;
    base.mkdirs();
    if (!base.isDirectory())
      throw new RuntimeException("no directory: " + base);
  }

  public ResourceLocator sub(String folder_name) {
    return new ResourceLocator(file(folder_name));
  }

  public <T> T tryLoad(T object) {
    return ObjectProperties.tryLoad(object, properties(object.getClass()));
  }

  /** @param object
   * @param string to which ".properties" is appended to define filename
   * @return */
  public <T> T tryLoad(T object, String string) {
    return ObjectProperties.tryLoad(object, properties(string));
  }

  public boolean trySave(Object object) {
    return ObjectProperties.trySave(object, properties(object.getClass()));
  }

  /** @param object
   * @param string to which ".properties" is appended to define filename
   * @return */
  public boolean trySave(Object object, String string) {
    return ObjectProperties.trySave(object, properties(string));
  }

  public File properties(Class<?> cls) {
    return properties(cls.getSimpleName());
  }

  public File properties(String string) {
    return file(string + FILE_EXTENSION);
  }

  /** @param name of file, for instance consisting of title and extension
   * @return file in given base directory with given name */
  public File file(String name) {
    return new File(base, name);
  }
}
