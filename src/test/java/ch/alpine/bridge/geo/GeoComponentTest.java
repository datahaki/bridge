// code by jph
package ch.alpine.bridge.geo;

import java.awt.Container;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.pro.ManipulateProvider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
class GeoComponentTest implements ManipulateProvider {
  @Override
  public Container getContainer() {
    GeoComponent geoComponent = new GeoComponent();
    geoComponent.tilePixel = new TilePixel(new Tile(4, 8, 8), 128, 230);
    return geoComponent;
  }

  @Test
  void test() {
    JFrame jFrame = new GeoComponentTest().runStandalone();
    jFrame.setVisible(false);
  }
}
