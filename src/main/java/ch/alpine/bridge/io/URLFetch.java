// code by jph
package ch.alpine.bridge.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/** Example:
 * <pre>
 * try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
 * urlFetch.downloadIfMissing(HomeDirectory.file("favicon.ico"));
 * }
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/URLFetch.html">URLFetch</a> */
public class URLFetch implements AutoCloseable {
  public static byte[] of(URI uri) throws IOException, InterruptedException {
    HttpRequest httpRequest = HttpRequest.newBuilder() //
        .uri(uri) //
        .header("User-Agent", "TileDownloader/1.0") //
        .GET().build();
    return HttpClient.newHttpClient() //
        .send(httpRequest, HttpResponse.BodyHandlers.ofByteArray()) //
        .body();
  }

  private static final int BUFFER_SIZE = 8192;
  // ---
  private final HttpURLConnection httpURLConnection;
  private final String contentType;
  private final int length;

  /** @param url
   * @throws IOException */
  public URLFetch(URL url) throws IOException {
    httpURLConnection = (HttpURLConnection) url.openConnection();
    int responseCode = httpURLConnection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      contentType = httpURLConnection.getContentType();
      length = httpURLConnection.getContentLength();
    } else {
      httpURLConnection.disconnect();
      throw new IOException("" + responseCode);
    }
  }

  public URLFetch(String url) throws IOException, URISyntaxException {
    this(new URI(url).toURL());
  }

  /** @return
   * @throws IOException */
  public InputStream inputStream() throws IOException {
    return httpURLConnection.getInputStream();
  }

  /** @param path to download web content to if file does not already exist,
   * or has the wrong length
   * @throws IOException */
  public void downloadIfMissing(Path path) throws IOException {
    if (Files.isRegularFile(path) && //
        Files.size(path) == length)
      return;
    download(path);
  }

  /** @param path to download web content to
   * @throws IOException if function was already called */
  public void download(Path path) throws IOException {
    try (InputStream inputStream = httpURLConnection.getInputStream()) {
      try (OutputStream outputStream = Files.newOutputStream(path)) {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1)
          outputStream.write(buffer, 0, bytesRead);
      }
    }
    httpURLConnection.disconnect();
  }

  /** @return number of bytes to download */
  public int length() {
    return length;
  }

  /** @return */
  public String contentType() {
    return contentType;
  }

  @Override // from AutoCloseable
  public void close() {
    httpURLConnection.disconnect();
  }
}
