// code by jph
package ch.alpine.bridge.geo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HexFormat;

class ImageMigrate {
  private final Path root;

  private ImageMigrate(Path root) {
    this.root = root;
  }

  void recur(Path directory) throws IOException {
    HexFormat hexFormat = HexFormat.of();
    // IO.println(" " + path.toFile());
    for (File file : directory.toFile().listFiles()) {
      if (file.isDirectory()) {
        recur(file.toPath());
      } else {
        Path path = file.toPath();
        if (Files.isRegularFile(path)) {
          final String string = file.getName();
          final int hash = string.hashCode();
          final String hi = hexFormat.toHexDigits((byte) ((hash >> 6) & 0x3f));
          final String lo = hexFormat.toHexDigits((byte) (hash & 0x3f));
          String[] splits = string.substring(0, string.indexOf('.')).split("_");
          String show = splits[0] + ":" + splits[1] + ":" + splits[2];
          IO.println(string + "\t" + show + "\t" + hi + " " + lo);
          Path dir = root.resolve(hi, lo);
          Files.createDirectories(dir);
          Path dst = dir.resolve(string);
          IO.println("src=" + path);
          IO.println("dst=" + dst);
          Files.move(path, dst, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
  }
  //
  // static void main() throws IOException {
  // TileServers ts = TileServers.OpenTopoMap;
  // Path path = ts.path();
  // Path root = HomeDirectory.Database.mk_dirs(ts.name());
  // IO.println(path);
  // ImageMigrate imageMigrate = new ImageMigrate(root);
  // if (Files.isDirectory(path))
  // imageMigrate.recur(path);
  // }
}
