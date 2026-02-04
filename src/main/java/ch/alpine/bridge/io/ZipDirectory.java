// code by jph
package ch.alpine.bridge.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory implements AutoCloseable {
  private static final FileFilter ALL = _ -> true;

  /** @param directory
   * @param zipFile
   * @param fileFilter
   * @throws FileNotFoundException
   * @throws IOException */
  public static void of(File directory, File zipFile, FileFilter fileFilter) throws FileNotFoundException, IOException {
    try (ZipDirectory zipDirectory = new ZipDirectory(zipFile, fileFilter)) {
      zipDirectory.visit(directory, directory.getName());
    }
  }

  /** @param directory
   * @param zipFile
   * @throws IOException
   * @throws FileNotFoundException */
  public static void of(File directory, File zipFile) throws FileNotFoundException, IOException {
    of(directory, zipFile, ALL);
  }

  // ---
  private final ZipOutputStream zipOutputStream;
  private final FileFilter fileFilter;

  private ZipDirectory(File dst, FileFilter fileFilter) throws FileNotFoundException {
    zipOutputStream = new ZipOutputStream(new FileOutputStream(dst));
    this.fileFilter = fileFilter;
  }

  private void visit(File directory, String prefix) throws IOException {
    for (File file : directory.listFiles()) {
      String name = prefix + '/' + file.getName(); // zip entries require / as file separator
      if (file.isDirectory())
        visit(file, name);
      else //
      if (fileFilter.accept(file)) {
        zipOutputStream.putNextEntry(new ZipEntry(name));
        zipOutputStream.write(Files.readAllBytes(file.toPath()));
        zipOutputStream.closeEntry();
      }
    }
  }

  @Override
  public void close() throws IOException {
    zipOutputStream.close();
  }
}
