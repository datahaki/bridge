// code by jph
package ch.alpine.bridge.fig.geo;

import java.net.URI;

import ch.alpine.tensor.ext.Integers;

public enum TileServer {
  OPENTOPOMAP("https://tile.opentopomap.org", 17);

  private final String server;
  private final int z_max;

  TileServer(String server, int z_max) {
    this.server = server;
    this.z_max = z_max;
  }

  public URI uri(int z, int x, int y) {
    Integers.requireLessEquals(z, z_max);
    return URI.create(server + "/" + z + "/" + x + "/" + y + ".png");
  }
}
