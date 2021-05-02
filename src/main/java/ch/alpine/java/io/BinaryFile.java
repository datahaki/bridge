// code by jph
package ch.alpine.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public enum BinaryFile {
  ;
  /** @param file
   * @return
   * @throws IOException
   * @throws FileNotFoundException */
  public static byte[] read(File file) throws FileNotFoundException, IOException {
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      byte[] data = new byte[fileInputStream.available()];
      fileInputStream.read(data);
      return data;
    }
  }

  /** @param file
   * @param data
   * @throws FileNotFoundException
   * @throws IOException */
  public static void write(File file, byte[] data) throws FileNotFoundException, IOException {
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      fileOutputStream.write(data);
    }
  }
}
