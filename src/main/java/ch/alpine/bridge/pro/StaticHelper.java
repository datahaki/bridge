// code by jph
package ch.alpine.bridge.pro;

import java.nio.file.Path;

import ch.alpine.tensor.ext.HomeDirectory;

enum StaticHelper {
  ;
  public static Path of(Class<?> cls) {
    return HomeDirectory._local_share.resolve(cls.getName().split("\\."));
  }
}
