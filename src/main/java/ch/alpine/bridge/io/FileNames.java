// code by jph
package ch.alpine.bridge.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public enum FileNames {
  ;
  /** @param start
   * @return */
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
