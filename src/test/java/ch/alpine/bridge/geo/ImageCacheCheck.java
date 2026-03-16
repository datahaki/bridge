// code by jph
package ch.alpine.bridge.geo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ch.alpine.bridge.io.DeleteDirectory;
import ch.alpine.tensor.Throw;

class ImageCacheCheck {
  void recur(Path directory, int depth) throws IOException {
    for (File file : directory.toFile().listFiles()) {
      boolean status = depth < 2 || file.isFile();
      if (!status) {
        System.err.println(file);
        try {
          DeleteDirectory.of(file.toPath(), 1, 10);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (depth < 2) {
        Throw.unless(file.isDirectory());
        recur(file.toPath(), depth + 1);
      }
    }
  }

  static void main() throws IOException {
    Path path = TileServers.OpenTopoMap.path();
    if (Files.isDirectory(path))
      new ImageCacheCheck().recur(path, 0);
  }
}
