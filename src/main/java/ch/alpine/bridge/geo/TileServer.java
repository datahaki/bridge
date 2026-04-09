// code by jph
package ch.alpine.bridge.geo;

public interface TileServer extends MapUri {
  int z_max();

  MapImagesCache cache();
}
