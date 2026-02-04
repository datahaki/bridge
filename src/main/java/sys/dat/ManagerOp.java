package sys.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.tensor.ext.ReadLine;

public enum ManagerOp {
  ;
  public static List<String> lines(File file) throws FileNotFoundException, IOException {
    file.createNewFile();
    try (InputStream inputStream = new FileInputStream(file)) {
      return ReadLine.of(inputStream) //
          .filter(string -> string.startsWith("! ")) //
          .map(string -> string.substring(2)) //
          .map(String::trim) //
          .collect(Collectors.toList());
    }
  }

  public static void manifest(List<String> list, File file) throws FileNotFoundException, IOException {
    try (OutputStream outputStream = new FileOutputStream(file)) {
      lines(list.stream().map(string -> "! " + string), outputStream);
    }
  }

  /* redundant to ExportHelper */
  public static void lines(Stream<String> stream, OutputStream outputStream) {
    try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
      stream.sequential().forEach(printWriter::println);
    } // writer close
  }
}
