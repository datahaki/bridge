// code by jph
package ch.alpine.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory implements AutoCloseable {
  /** @param directory
   * @param zipFile
   * @throws IOException
   * @throws FileNotFoundException */
  public static void of(File directory, File zipFile) throws FileNotFoundException, IOException {
    try (ZipDirectory zipDirectory = new ZipDirectory(zipFile)) {
      zipDirectory.visit(directory, directory.getName());
    }
  }

  /***************************************************/
  private final ZipOutputStream zipOutputStream;

  private ZipDirectory(File dst) throws FileNotFoundException {
    zipOutputStream = new ZipOutputStream(new FileOutputStream(dst));
  }

  private void visit(File directory, String prefix) throws IOException {
    for (File file : directory.listFiles()) {
      String name = prefix + '/' + file.getName(); // zip entries require / as file separator
      if (file.isDirectory())
        visit(file, name);
      else {
        zipOutputStream.putNextEntry(new ZipEntry(name));
        zipOutputStream.write(BinaryFile.read(file));
        zipOutputStream.closeEntry();
      }
    }
  }

  @Override
  public void close() throws IOException {
    zipOutputStream.close();
  }
}
