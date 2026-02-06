// code by jph
package ch.alpine.bridge.io;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory implements AutoCloseable {
  private static final FileFilter ALL = _ -> true;

  /** @param directory
   * @param zipFile
   * @param fileFilter
   * @throws FileNotFoundException
   * @throws IOException */
  public static void of(Path directory, Path zipFile, FileFilter fileFilter) throws FileNotFoundException, IOException {
    try (ZipDirectory zipDirectory = new ZipDirectory(zipFile, fileFilter)) {
      zipDirectory.visit(directory, directory.getFileName().toString());
    }
  }

  /** @param directory
   * @param zipFile
   * @throws IOException
   * @throws FileNotFoundException */
  public static void of(Path directory, Path zipFile) throws FileNotFoundException, IOException {
    of(directory, zipFile, ALL);
  }

  // ---
  private final ZipOutputStream zipOutputStream;
  private final FileFilter fileFilter;

  private ZipDirectory(Path dst, FileFilter fileFilter) throws IOException {
    zipOutputStream = new ZipOutputStream(Files.newOutputStream(dst));
    this.fileFilter = fileFilter;
  }

  private void visit(Path directory, String prefix) throws IOException {
    for (Path path : Files.list(directory).toList()) {
      String name = prefix + '/' + path.getFileName(); // zip entries require / as file separator
      if (Files.isDirectory(path))
        visit(path, name);
      else //
      if (fileFilter.accept(path.toFile())) {
        zipOutputStream.putNextEntry(new ZipEntry(name));
        zipOutputStream.write(Files.readAllBytes(path));
        zipOutputStream.closeEntry();
      }
    }
  }

  @Override
  public void close() throws IOException {
    zipOutputStream.close();
  }
}
