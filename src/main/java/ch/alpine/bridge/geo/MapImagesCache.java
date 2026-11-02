// code by jph
package ch.alpine.bridge.geo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;

import ch.alpine.bridge.io.URIRead;
import ch.alpine.tensor.ext.LruCache;

public class MapImagesCache {
  private static final HexFormat HEX_FORMAT = HexFormat.of();
  // ---
  private final Path root;
  private final MapUri mapUri;
  /** some bounded set of available images to prevent
   * re-reading from hard drive */
  private final LruCache<Tile, TileImage> lruCache = new LruCache<>(256);
  /** queue for thread to download */
  private final Set<Tile> set = new LinkedHashSet<>();
  /** logic of thread */
  private final Runnable runnable = new Runnable() {
    @Override
    public void run() {
      while (true) {
        Tile tile;
        synchronized (set) {
          if (set.isEmpty())
            break;
          Iterator<Tile> iterator = set.iterator();
          tile = iterator.next();
          // iterator.remove();
        }
        Path path = path(tile);
        if (Files.isRegularFile(path))
          IO.println("skip");
        else
          try {
            Files.createDirectories(path.getParent());
            URI uri = mapUri.uri(tile.z(), tile.x(), tile.y());
            byte[] data = URIRead.of(uri);
            synchronized (root) {
              Files.write(path, data);
              IO.println("uri " + uri);
            }
          } catch (Exception exception) {
            System.err.println(tile);
          }
        synchronized (set) {
          set.remove(tile);
        }
      }
      synchronized (runnable) {
        IO.println("stop " + thread);
        thread = null;
      }
    }
  };
  /** non final thread */
  private Thread thread = null;

  public MapImagesCache(Path root, MapUri mapUri) {
    this.root = root;
    this.mapUri = mapUri;
  }

  public void clearQueue() {
    synchronized (set) {
      set.clear();
    }
  }

  /** @param tile
   * @return buffered image that for open street/topo map is of type
   * {@link BufferedImage#TYPE_BYTE_INDEXED} */
  public TileImage getTile(Tile tile) {
    {
      TileImage tileImage = lruCache.get(tile);
      if (Objects.nonNull(tileImage))
        return tileImage;
    }
    {
      TileImage tileImage = readFile(tile);
      if (Objects.nonNull(tileImage)) {
        lruCache.put(tile, tileImage);
        return tileImage;
      }
    }
    synchronized (set) {
      set.add(tile);
    }
    synchronized (runnable) {
      if (Objects.isNull(thread)) {
        thread = new Thread(runnable);
        thread.start();
      }
    }
    return null;
  }

  /** function checks if tile is available offline
   * if not, attempts to download tile and store to database
   * 
   * @param tile
   * @return image as in database, or null */
  private TileImage readFile(Tile tile) {
    Path path = path(tile);
    if (Files.isRegularFile(path))
      try {
        try {
          synchronized (root) {
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
    return null;
  }

  public boolean isAvailableOffline(Tile tile) {
    return Files.isRegularFile(path(tile));
  }

  public Path path(Tile tile) {
    final String string = String.format("%d_%d_%d.png", tile.z(), tile.x(), tile.y());
    final int hash = string.hashCode();
    final String hi = HEX_FORMAT.toHexDigits((byte) ((hash >> 6) & 0x3f));
    final String lo = HEX_FORMAT.toHexDigits((byte) (hash & 0x3f));
    return root.resolve(hi, lo, string);
  }
}
