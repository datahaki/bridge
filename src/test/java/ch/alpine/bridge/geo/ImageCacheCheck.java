// code by jph
package ch.alpine.bridge.geo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import ch.alpine.bridge.io.DeleteDirectory;
import ch.alpine.tensor.Throw;

class ImageCacheCheck {
  void recur(Path directory, int depth) throws IOException {
    for (File file : directory.toFile().listFiles()) {
      boolean status = depth < 2 || file.isFile();
      if (!status) {
        try {
          System.err.println("delete dir: " + file);
          DeleteDirectory.of(file.toPath(), 1, 10);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (depth < 2) {
        Throw.unless(file.isDirectory());
        recur(file.toPath(), depth + 1);
      } else {
        Throw.unless(file.isFile());
        try {
          ImageIO.read(file);
        } catch (Exception e) {
          System.err.println("delete file: " + file);
          Files.delete(file.toPath());
        }
      }
    }
  }

  static void main() throws IOException {
    for (TileServers ts : TileServers.values()) {
      Path path = ts.path();
      if (Files.isDirectory(path))
        new ImageCacheCheck().recur(path, 0);
    }
  }
}
