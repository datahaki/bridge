// code by jph
package ch.alpine.bridge.geo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;

import javax.imageio.ImageIO;

import ch.alpine.bridge.io.URIRead;
import ch.alpine.tensor.ext.Cache;

public class MapImagesCache {
  private final Cache<Tile, BufferedImage> cache = Cache.of(this::getSafe, 3 * 128);
  private final HexFormat hexFormat = HexFormat.of();
  private final Path root;
  private final MapUri mapUri;
  public boolean debug_print = false;
  private int downloads = 0;

  public MapImagesCache(Path root, MapUri mapUri) {
    this.root = root;
    this.mapUri = mapUri;
  }

  /** @param tile
   * @return buffered image that for open street/topo map is of type
   * {@link BufferedImage#TYPE_BYTE_INDEXED} */
  public BufferedImage getTile(Tile tile) {
    return cache.apply(tile);
  }

  public int getDownloadCount() {
    return downloads;
  }

  private BufferedImage getSafe(Tile tile) {
    Path path = path(tile);
    try {
      if (!Files.isRegularFile(path)) {
        ++downloads;
        Files.createDirectories(path.getParent());
        URI uri = mapUri.uri(tile.z(), tile.x(), tile.y());
        if (debug_print)
          IO.println("uri " + uri);
        Files.write(path, URIRead.of(uri));
      }
      try {
        return ImageIO.read(path.toFile());
      } catch (IOException exception) {
        System.err.println(path);
        Files.delete(path);
        throw new UncheckedIOException(exception);
      }
    } catch (Exception exception) {
      System.err.println(tile + " " + path(tile));
    }
    return null;
  }

  public Path path(Tile tile) {
    final String string = String.format("%d_%d_%d.png", tile.z(), tile.x(), tile.y());
    final int hash = string.hashCode();
    final String hi = hexFormat.toHexDigits((byte) ((hash >> 6) & 0x3f));
    final String lo = hexFormat.toHexDigits((byte) (hash & 0x3f));
    return root.resolve(hi, lo, string);
  }

  public boolean isAvailableOffline(Tile tile) {
    return Files.isRegularFile(path(tile));
  }
}
