// code by jph
package ch.alpine.bridge.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** Example:
 * <pre>
 * try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
 * urlFetch.downloadIfMissing(HomeDirectory.file("favicon.ico"));
 * }
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/URLRead.html">URLRead</a> */
public enum URLRead {
  ;
  /** @param uri
   * @return
   * @throws IOException
   * @throws InterruptedException */
  public static byte[] of(URI uri) throws IOException, InterruptedException {
    return HttpClient.newHttpClient().send( //
        HttpRequest.newBuilder().uri(uri).GET().build(), //
        HttpResponse.BodyHandlers.ofByteArray()) //
        .body();
  }

  public static byte[] of(String uri) throws IOException, InterruptedException, URISyntaxException {
    return of(new URI(uri));
  }
}
