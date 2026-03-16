// code by jph
package ch.alpine.bridge.fig.geo;

import java.net.URI;

public enum TileServer {
  OPENTOPOMAP("https://tile.opentopomap.org");

  private final String server;

  TileServer(String server) {
    this.server = server;
  }

  public URI uri(int z, int x, int y) {
    return URI.create(server + "/" + z + "/" + x + "/" + y + ".png");
  }
}
