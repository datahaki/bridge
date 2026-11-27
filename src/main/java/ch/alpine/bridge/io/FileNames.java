// code by jph
package ch.alpine.bridge.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public enum FileNames {
  ;
  /** @param start directory
   * @return stream of files (not directories) in start directory and in all sub directories */
  public static Stream<Path> all(Path start) {
    try {
      Path root = start.toAbsolutePath().normalize();
      return Files.find( //
          root, //
          Integer.MAX_VALUE, //
          (_, bfa) -> !bfa.isDirectory());
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }
  }
}
