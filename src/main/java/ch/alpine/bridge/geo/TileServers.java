// code by jph
package ch.alpine.bridge.geo;

import java.net.URI;
import java.nio.file.Path;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.Integers;

public enum TileServers implements TileServer {
  OpenStreetMap("https://tile.openstreetmap.org", 19),
  OpenTopoMap("https://tile.opentopomap.org", 17), //
  ;

  private final String server;
  private final int z_max;

  TileServers(String server, int z_max) {
    this.server = server;
    this.z_max = z_max;
  }

  @Override
  public URI uri(int z, int x, int y) {
    Integers.requireLessEquals(z, z_max);
    return URI.create(server + "/" + z + "/" + x + "/" + y + ".png");
  }

  @Override
  public int z_max() {
    return z_max;
  }

  @Override
  public MapImagesCache cache() {
    return new MapImagesCache(path(), this);
  }

  public Path path() {
    return HomeDirectory.Database.mk_dirs(name());
  }
}
