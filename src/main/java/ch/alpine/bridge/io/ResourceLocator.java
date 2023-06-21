// code by jph
package ch.alpine.bridge.io;

import java.io.File;

import ch.alpine.bridge.ref.util.ObjectProperties;

public class ResourceLocator {
  final File root;

  public ResourceLocator(File root) {
    this.root = root;
    root.mkdirs();
  }

  public <T> T tryLoad(T object) {
    return ObjectProperties.tryLoad(object, file(object));
  }

  public boolean trySave(Object object) {
    return ObjectProperties.trySave(object, file(object));
  }

  private File file(Object object) {
    String title = object.getClass().getSimpleName() + ".properties";
    return new File(root, title);
  }
}
