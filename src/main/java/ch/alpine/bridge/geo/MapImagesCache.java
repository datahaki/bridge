// code by jph
package ch.alpine.bridge.geo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.Objects;

import javax.imageio.ImageIO;

import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.ext.PathName;

public class MapImagesCache {
  private final BufferedImage fallback = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
  private final Cache<Tile, BufferedImage> cache = Cache.of(this::getSafe, 256);
  private final HexFormat hexFormat = HexFormat.of();
  private final Path root;
  private final TileServer tileServer;

  public MapImagesCache(Path root, TileServer tileServer) {
    this.root = root;
    this.tileServer = tileServer;
    {
      Graphics2D graphics = fallback.createGraphics();
      graphics.setColor(Color.RED);
      graphics.drawLine(0, 0, 255, 255);
      graphics.drawLine(0, 255, 255, 0);
      graphics.dispose();
    }
  }

  public BufferedImage getTile(Tile tile) {
    return cache.apply(tile);
  }

  private BufferedImage getSafe(Tile tile) {
    try {
      return Objects.requireNonNull(get(tile));
    } catch (Exception exception) {
      System.err.println(tile + " " + path(tile));
    }
    return fallback;
  }

  private BufferedImage get(Tile tile) throws IOException, InterruptedException {
    Path path = path(tile);
    if (!Files.isRegularFile(path))
      download(tile, path);
    try {
      return ImageIO.read(path.toFile());
    } catch (IOException exception) {
      System.err.println(path);
      throw new UncheckedIOException(exception);
    }
  }

  private void download(Tile tile, Path path) throws IOException, InterruptedException {
    URI uri = tileServer.uri(tile.z(), tile.x(), tile.y());
    HttpRequest httpRequest = HttpRequest.newBuilder() //
        .uri(uri) //
        .header("User-Agent", "TileDownloader/1.0") //
        .GET().build();
    HttpResponse<byte[]> httpResponse = HttpClient.newHttpClient() //
        .send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    Files.createDirectories(PathName.of(path).parent());
    Files.write(path, httpResponse.body());
  }

  private Path path(Tile tile) {
    final String string = String.format("%d_%d_%d.png", tile.z(), tile.x(), tile.y());
    final int hash = string.hashCode();
    final String hi = hexFormat.toHexDigits((byte) ((hash >> 6) & 0x3f));
    final String lo = hexFormat.toHexDigits((byte) (hash & 0x3f));
    return root.resolve(hi, lo, string);
  }

  static void main() {
    MapImagesCache urlPathCache = TileServers.OpenTopoMap.createCache();
    final int z = 7;
    for (int iz = 0; iz <= z; ++iz) {
      int max = Tile.maxInclusive(iz);
      for (int ix = 0; ix <= max; ++ix)
        for (int iy = 0; iy <= max; ++iy) {
          Tile tile = new Tile(z, ix, iy);
          urlPathCache.getSafe(tile);
        }
    }
  }
}
