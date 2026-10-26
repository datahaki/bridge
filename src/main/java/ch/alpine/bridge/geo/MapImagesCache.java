// code by jph
package ch.alpine.bridge.geo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HexFormat;
import java.util.Objects;

import javax.imageio.ImageIO;

import ch.alpine.bridge.io.URIRead;
import ch.alpine.tensor.ext.Cache;

public class MapImagesCache {
  private final Cache<Tile, TileImage> cache = Cache.of(this::getSafe, 3 * 128);
  private final HexFormat hexFormat = HexFormat.of();
  private final Path root;
  private final MapUri mapUri;
  private final Deque<Tile> deque = new ArrayDeque<>();
  private Thread thread = null;
  private final Runnable runnable = new Runnable() {
    @Override
    public void run() {
      while (!deque.isEmpty()) {
        Tile tile;
        synchronized (deque) {
          tile = deque.pollFirst();
        }
        downloadTile(tile);
      }
      IO.println("thread stop");
      thread = null;
    }
  };

  public MapImagesCache(Path root, MapUri mapUri) {
    this.root = root;
    this.mapUri = mapUri;
  }

  void queue(Tile tile) {
    synchronized (deque) {
      deque.add(tile);
    }
    if (Objects.isNull(thread)) {
      thread = new Thread(runnable);
      IO.println("thread " + thread);
      thread.start();
    }
  }

  public void clearQueue() {
    synchronized (deque) {
      deque.clear();
    }
  }

  /** @param tile
   * @return buffered image that for open street/topo map is of type
   * {@link BufferedImage#TYPE_BYTE_INDEXED} */
  public TileImage getTile(Tile tile) {
    return cache.apply(tile);
  }


  /** function checks if tile is available offline
   * if not, attempts to download tile and store to database
   * 
   * @param tile
   * @return image as in database, or null */
  private TileImage getSafe(Tile tile) {
    Path path = path(tile);
    if (Files.isRegularFile(path))
      try {
        try {
          synchronized (this) {
            BufferedImage bufferedImage = ImageIO.read(path.toFile());
            return new TileImage(bufferedImage, Files.size(path));
          }
        } catch (IOException exception) {
          System.err.println(path);
          Files.delete(path);
          throw new UncheckedIOException(exception);
        }
      } catch (Exception exception) {
        // System.err.println(tile + " " + path(tile));
      }
    queue(tile);
    return null;
  }

  private void downloadTile(Tile tile) {
    Path path = path(tile);
    try {
      Files.createDirectories(path.getParent());
      URI uri = mapUri.uri(tile.z(), tile.x(), tile.y());
      byte[] data = URIRead.of(uri);
      synchronized (this) {
        Files.write(path, data);
        IO.println("uri " + uri);
      }
    } catch (Exception exception) {
      // System.err.println(tile + " " + path(tile));
    }
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
