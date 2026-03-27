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
    return new GeometricComponent();
  }

  @Test
  void test() {
    JFrame jFrame = new GeometricComponentTest().runStandalone();
    jFrame.setVisible(false);
  }
}
