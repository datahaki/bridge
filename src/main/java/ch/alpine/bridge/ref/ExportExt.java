// code by jph
package ch.alpine.bridge.ref;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

// TODO BRIDGE obsolete with next tensor lib release
public enum ExportExt {
  ;
  /** similar to {@link Properties#store(java.io.Writer, String)}
   * but without the leading timestamp
   * 
   * @param file
   * @param properties
   * @throws IOException */
  public static void properties(File file, Properties properties) throws IOException {
    StringWriter stringWriter = new StringWriter();
    properties.store(stringWriter, null);
    String string = stringWriter.toString();
    // first line is of the following form:
    // #Wed Jun 01 13:25:44 CEST 2022
    if (string.startsWith("#")) {
      BufferedReader bufferedReader = new BufferedReader(new StringReader(string));
      List<String> list = bufferedReader.lines().skip(1).collect(Collectors.toList());
      Files.write(file.toPath(), (Iterable<String>) list::iterator);
    } else
      throw new IllegalStateException(string);
  }
}
