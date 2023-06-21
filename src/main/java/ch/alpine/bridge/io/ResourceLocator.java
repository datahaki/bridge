// code by jph
package ch.alpine.bridge.io;

import java.io.File;

import ch.alpine.bridge.ref.util.ObjectProperties;

public final class ResourceLocator {
  private final File root;

  public ResourceLocator(File root) {
    this.root = root;
    root.mkdirs();
    if (!root.isDirectory())
      throw new RuntimeException("no directory: " + root);
  }

  public <T> T tryLoad(T object) {
    return ObjectProperties.tryLoad(object, file(object));
  }

  public boolean trySave(Object object) {
    return ObjectProperties.trySave(object, file(object));
  }

  private File file(Object object) {
    return new File(root, object.getClass().getSimpleName() + ".properties");
  }
}
