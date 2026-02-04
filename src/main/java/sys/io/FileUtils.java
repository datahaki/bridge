// code by jph
package sys.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.bridge.io.DeleteDirectory;
import sys.Filename;

public enum FileUtils {
  ;
  public static boolean isIdentical(File file, File dest) throws FileNotFoundException, IOException {
    return Arrays.equals(Files.readAllBytes(dest.toPath()), Files.readAllBytes(file.toPath()));
  }

  public static boolean copyFileIfDifferent(File file, File dest) throws IOException {
    if (dest.exists() && isIdentical(file, dest))
      return false;
    // ---
    // code modified from StackOverflow
    if (!dest.exists())
      dest.createNewFile();
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      try (FileChannel fileChannel = fileInputStream.getChannel()) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
          try (FileChannel fileChannel2 = fileOutputStream.getChannel()) {
            fileChannel2.transferFrom(fileChannel, 0, fileChannel.size());
          }
        }
      }
    }
    return true;
  }

  public static void rigorousTransfer(File file, File dest, int max_depth, int max_count) throws IOException {
    if (!file.getName().equalsIgnoreCase(dest.getName()))
      throw new RuntimeException();
    if (!file.exists())
      throw new RuntimeException("source " + file + " does not exist");
    // ---
    DeleteDirectory.of(dest, max_depth, max_count);
    copyRecursively(file, dest.getParentFile());
  }

  private static void copyRecursively(File file, File dest) throws FileNotFoundException, IOException {
    if (file.isDirectory()) {
      File subDst = new File(dest, file.getName());
      subDst.mkdirs(); // act of creation
      for (File entry : file.listFiles())
        copyRecursively(entry, subDst); // proceed on level up
    } else
      Files.write(new File(dest, file.getName()).toPath(), Files.readAllBytes(file.toPath())); // act of creation
  }

  public static List<Filename> listFiles(File directory, String... strings) {
    Set<String> set = Stream.of(strings).map(String::toLowerCase).collect(Collectors.toSet());
    return Stream.of(directory.listFiles()) //
        .filter(File::isFile) //
        .map(Filename::new) //
        .filter(filename -> set.contains(filename.extension().toLowerCase())) //
        .sorted() //
        .toList();
  }
}
