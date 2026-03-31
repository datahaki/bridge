// code by jph
package ch.alpine.bridge.geo;

import java.net.URI;

public interface TileServer {
  URI uri(int z, int x, int y);

  int z_max();

  MapImagesCache cache();
}
