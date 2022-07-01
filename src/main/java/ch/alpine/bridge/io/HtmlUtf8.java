// code by jph
package ch.alpine.bridge.io;

import java.awt.Color;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** HtmlUtf8 exports strings to html pages in utf-8 encoding. All logs of MissionControl are exported with HtmlUtf8. */
public abstract class HtmlUtf8 implements Closeable {
  protected static final Charset CHARSET = StandardCharsets.UTF_8;

  /** @param file
   * @return */
  public static HtmlUtf8 page(File file) {
    if (file.exists())
      file.delete();
    String string;
    string = "<!DOCTYPE html>\n<html>\n";
    string += "<head>\n";
    string += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n";
    string += "</head>\n<body>\n";
    HtmlUtf8 htmlUtf8 = new BufferedHtmlUtf8(file);
    htmlUtf8.append(string);
    return htmlUtf8;
  }

  /** @param file
   * @param title
   * @param split for instance: cols="300,*"
   * @param fileStringL
   * @param nameStringL
   * @param fileStringR
   * @param nameStringR */
  public static void index(File file, String title, //
      String split, // cols="300,*"
      String fileStringL, String nameStringL, //
      String fileStringR, String nameStringR) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("<html>\n");
      if (Objects.nonNull(title) && !title.isEmpty())
        stringBuilder.append("<head><title>" + title + "</title></head>\n");
      stringBuilder.append("<frameset " + split + ">\n");
      stringBuilder.append("<frame src=\"" + fileStringL + "\" name=\"" + nameStringL + "\">\n");
      stringBuilder.append("<frame src=\"" + fileStringR + "\" name=\"" + nameStringR + "\">\n");
      stringBuilder.append("</frameset>\n</html>\n");
      try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), CHARSET)) {
        outputStreamWriter.write(stringBuilder.toString());
      }
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public static String color(Color color) {
    return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
  }

  // ---
  public final File file;

  protected HtmlUtf8(File file) {
    this.file = file;
  }

  public void append(Object object) {
    private_append(object);
  }

  public void appendln(Object object) {
    private_append(object + "\n");
  }

  public void appendln() {
    append("\n");
  }

  protected abstract void private_append(Object object);

  @Override
  public void close() {
    append("</body>\n</html>\n");
  }
}
