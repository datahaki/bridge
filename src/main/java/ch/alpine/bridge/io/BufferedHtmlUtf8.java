// code by jph
package ch.alpine.bridge.io;

import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

class BufferedHtmlUtf8 extends HtmlUtf8 {
  private final StringBuilder stringBuilder = new StringBuilder();

  protected BufferedHtmlUtf8(Path path) {
    super(path);
  }

  @Override
  protected void private_append(Object object) {
    stringBuilder.append(object);
  }

  @Override
  public void close() {
    super.close();
    try (OutputStreamWriter outputStreamWriter = //
        new OutputStreamWriter(Files.newOutputStream(path), CHARSET)) {
      outputStreamWriter.write(stringBuilder.toString());
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
