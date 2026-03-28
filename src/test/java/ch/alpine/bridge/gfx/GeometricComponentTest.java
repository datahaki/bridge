// code by jph
package ch.alpine.bridge.gfx;

import java.awt.Container;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.pro.ManipulateProvider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
class GeometricComponentTest implements ManipulateProvider {
  @Override
  public Container getContainer() {
    GeometricComponent geometricComponent = new GeometricComponent();
    geometricComponent.showTimings();
    return geometricComponent;
  }

  @Test
  void test() {
    JFrame jFrame = new GeometricComponentTest().runStandalone();
    jFrame.setVisible(false);
  }
}
